package spotify_app.models;

public class Track extends Song {
    private String artist;
    private String genre;

    public Track(String id, String title, int duration, String artist, String genre) {
        super(id, title, duration);
        this.artist = artist;
        this.genre = genre;
    }

    public String getArtist() { 
        return artist; 
    }

    public String getGenre() { 
        return genre; 
    }

    @Override
    public void download() {
        System.out.println("Downloading track: " + title);
    }
}
