package server.core;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MusicServer {
    private static final Logger logger = LogManager.getRootLogger();
    private Thread serverThread;
    private final int port;
    @Getter
    private final MusicStreamManager musicStreamManager;

    public MusicServer(int port, MusicStreamManager musicStreamManager) {
        this.port = port;
        this.musicStreamManager = musicStreamManager;
        new Thread(musicStreamManager).start();
    }

    public void start() {
        serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!serverSocket.isClosed() && !Thread.currentThread().isInterrupted()) {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(clientSocket);
                    musicStreamManager.addClient(client);
                    logger.info("New client!");
                }
            } catch (IOException e) {
                if (!Thread.currentThread().isInterrupted()) {
                    e.printStackTrace();
                }
            }
        });

        serverThread.start();
    }

    public void stop() {
        if (serverThread != null) {
            serverThread.interrupt();
        }
        musicStreamManager.stop(); // Припускаючи, що у MusicStreamManager є метод stop
    }
}

