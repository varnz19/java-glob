package spotify_app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String username;
    private String email;
    private List<Song> likedSongs = new ArrayList<>();
    private List<Artist> followedArtists = new ArrayList<>();

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getName() {
        return username;
    }

    public Playlist createPlaylist(String name) {
        return new Playlist(name);
    }

    public void likeSong(Song s) {
        likedSongs.add(s);
    }

    public void followArtist(Artist a) {
        followedArtists.add(a);
    }
}
