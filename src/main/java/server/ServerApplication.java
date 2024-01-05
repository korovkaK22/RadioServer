package server;


import server.adapter.PlaylistAdapter;
import server.core.MusicServer;
import server.utils.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import server.visitor.ServerStatisticsVisitor;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class ServerApplication {


    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
        PlaylistAdapter adapter = context.getBean(PlaylistAdapter.class);
        List<File> playlist1 = adapter.getSongsFromPlaylist("playlist1");
        List<File> playlist2 = adapter.getSongsFromPlaylist("playlist2");


        MusicServer server1 = ServerFactory.getConfiguredMusicServer(5081, playlist1);
        server1.start();

        MusicServer server2 = ServerFactory.getConfiguredMusicServer(5082, playlist1);
        server2.start();

        ServerStatisticsVisitor visitor = new ServerStatisticsVisitor();
        visitor.visit(server1.getMusicStreamManager());
        Thread.sleep(1000_0);
        System.out.println(visitor.getStringStatistics());

    }





}
