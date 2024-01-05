package server.utils;

import server.core.MusicServer;
import server.core.MusicStreamManager;

import java.io.File;
import java.util.List;

public class ServerFactory {

    public static MusicServer getConfiguredMusicServer(int port, List<File> songs){
        return new MusicServer(port, getMusicStreamManager(songs));
    }

    public static MusicStreamManager getMusicStreamManager(List<File> songs){
        return new MusicStreamManager(songs);
    }


}
