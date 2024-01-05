package server.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import server.adapter.PlaylistAdapter;
import server.utils.*;

@Configuration
@PropertySource("classpath:files.properties")
public class ServerConfiguration {


    @Bean
    public PlaylistAdapter getPlaylistAdapter(@Value("${playlist.file}") String filePath){
        return new PlaylistAdapter(filePath, SongFinder.findSongs());
        }



}
