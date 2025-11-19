package spotify_app.core;

import java.io.Serializable;
import java.util.List;

public class PlaylistDTO implements Serializable {

    private String name;
    private List<String> songs;

    public PlaylistDTO(String name, List<String> songs) {
        this.name = name;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public List<String> getSongs() {
        return songs;
    }
}
