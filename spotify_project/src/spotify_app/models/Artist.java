package spotify_app.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Artist implements Serializable {
private static final long serialVersionUID = 1L;
private String id;
private String name;
private List<Song> songs = new ArrayList<>();


public Artist(String id, String name) {
this.id = id;
this.name = name;
}


public void addSong(Song s) {
songs.add(s);
}


public String getId() { return id; }
public String getName() { return name; }
public List<Song> getSongs() { return songs; }
}