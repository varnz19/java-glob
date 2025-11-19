package spotify_app.models;

public class Podcast extends Song {
    private String host;
    private String category;

    public Podcast(String id, String title, int duration, String host, String category) {
        super(id, title, duration);
        this.host = host;
        this.category = category;
    }

    public String getHost() { return host; }

    @Override
    public void download() {
        System.out.println("Downloading podcast: " + title);
    }
}
