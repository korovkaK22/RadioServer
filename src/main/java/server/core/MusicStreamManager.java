package server.core;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.iterator.MusicIterator;
import server.visitor.StatisticsVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class MusicStreamManager implements Runnable {
    private static final Logger logger = LogManager.getRootLogger();
    @Getter
    private final List<File> songs;
    @Getter
    private final List<ClientHandler> clients = new ArrayList<>();
    @Getter
    private volatile int allClientJoinsAmount =0;
    @Getter
    int playedSongsAmount =0;
    private volatile boolean running = true;
    private long currentSongPosition = 0;

    public MusicStreamManager(List<File> songs) {
        this.songs = songs;
    }

    public synchronized void addClient(ClientHandler client) {
        allClientJoinsAmount++;
        clients.add(client);
    }

    private synchronized void broadcast(byte[] buffer, int count) {
        Iterator<ClientHandler> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientHandler client = iterator.next();
            try {
                client.sendData(buffer, count);
            } catch (IOException e) {
                iterator.remove();
                try {
                    client.closeConnection();
                    logger.info("Can't send to client information. Kicked client");
                } catch (Exception ex) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        MusicIterator iterator = new MusicIterator(songs.toArray(new File[0]));
        while (running) {
            if (iterator.hasNext()) {
                File currentSong = iterator.next();
                playFile(currentSong);
                playedSongsAmount++;
            } else {
                iterator = new MusicIterator(songs.toArray(new File[0]));
            }
        }
    }

    private void playFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                currentSongPosition += count;
                broadcast(buffer, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accept(StatisticsVisitor visitor) {
        visitor.visit(this);
    }


    public synchronized void stop() {
        running = false;
    }
}