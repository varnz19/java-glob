package spotify_app.core;

public class AudioStreamer extends Thread {

    private final String trackName;

    public AudioStreamer(String trackName) {
        this.trackName = trackName;
    }

    @Override
    public void run() {
        try {
            System.out.println("🎧 Streaming: " + trackName);
            Thread.sleep(1000);
            System.out.println("✔ Finished streaming: " + trackName);
        } catch (InterruptedException e) {
            System.out.println("⛔ Streaming interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
