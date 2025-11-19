package spotify_app.models;

public class Audiobook extends Song {
    private String author;
    private String narrator;

    public Audiobook(String id, String title, int duration, String author, String narrator) {
        super(id, title, duration);
        this.author = author;
        this.narrator = narrator;
    }

    @Override
    public void download() {
        System.out.println("Downloading audiobook: " + title);
    }
}
