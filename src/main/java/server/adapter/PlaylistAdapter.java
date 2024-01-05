package server.adapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistAdapter {
    private final Map<String, List<String>> playlistMap;
    private final List<File> allSongs;

    public PlaylistAdapter(String jsonFilePath, List<File> allSongs) {
        this.playlistMap = new HashMap<>();
        this.allSongs = allSongs;
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            parseJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray playlists = jsonObject.getJSONArray("playlists");

        for (int i = 0; i < playlists.length(); i++) {
            JSONObject playlist = playlists.getJSONObject(i);
            String name = playlist.getString("name");
            JSONArray songs = playlist.getJSONArray("songs");

            List<String> songNames = new ArrayList<>();
            for (int j = 0; j < songs.length(); j++) {
                songNames.add(songs.getString(j));
            }

            playlistMap.put(name, songNames);
        }
    }

    public List<File> getSongsFromPlaylist(String playlistName) {
        List<File> playlistSongs = new ArrayList<>();
        List<String> songNames = playlistMap.get(playlistName);

        if (songNames != null) {
            for (String songName : songNames) {
                for (File songFile : allSongs) {
                    if (songFile.getName().contains(songName)) {
                        playlistSongs.add(songFile);
                    }
                }
            }
        }

        return playlistSongs;
    }
}
