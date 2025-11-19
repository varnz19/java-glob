package spotify_app.models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String name;
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    public String getName() {
        return name;
    }

    public List<String> getSongTitles() {
        List<String> titles = new ArrayList<>();
        for (Song s : songs) {
            titles.add(s.getTitle());
        }
        return titles;
    }
}
