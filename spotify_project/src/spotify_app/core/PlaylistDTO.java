package spotify_app.core;

import java.io.Serializable;
import java.util.List;

public class PlaylistDTO implements Serializable {

    private String playlistName;
    private List<String> songTitles;

    public PlaylistDTO(String playlistName, List<String> songTitles) {
        this.playlistName = playlistName;
        this.songTitles = songTitles;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<String> getSongTitles() {
        return songTitles;
    }
}
