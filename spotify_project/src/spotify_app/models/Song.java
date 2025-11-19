package spotify_app.models;

import java.io.Serializable;

public abstract class Song implements Serializable {
    protected String id;
    protected String title;
    protected int duration; // in seconds

    public Song(String id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getDuration() { return duration; }

    // Format 200 → "3:20"
    public String getFormattedDuration() {
        int m = duration / 60;
        int s = duration % 60;
        return String.format("%d:%02d", m, s);
    }

    public void play() {
        System.out.println("Playing: " + title);
    }

    // all children (Track/Podcast/Audiobook) must implement this later
    public abstract void download();
}
