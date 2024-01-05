package server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class SongFinder {

    public static List<File> getSongsByDirectories(String path){
        List<File> songFiles = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            List<File> songsInDir = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".mp3"))
                    .map(Path::toFile).toList();

            songFiles.addAll(songsInDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songFiles;
    }


    public static List<File> findSongs() {
        Properties properties = new Properties();
        try {
            // Завантаження properties-файлу
            properties.load(new FileInputStream("src/main/resources/files.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Отримання масиву папок
        String[] directories = properties.getProperty("songs.directories").split(",\\s");
        List<File> songFiles = new ArrayList<>();
        for (String directory : directories) {
            songFiles.addAll(getSongsByDirectories(directory));
        }
        return songFiles;
    }
}
