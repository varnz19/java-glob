package spotify_app.models;

import spotify_app.core.*;
import spotify_app.gui.SpotifyGUI;

import java.util.*;
import javax.swing.SwingUtilities;

public class SpotifyMain {

    public static Map<String, Song> library = new LinkedHashMap<>();
    public static LinkedList<Song> queue = new LinkedList<>();
    public static List<Playlist> allPlaylists = new ArrayList<>();
    public static Map<String, Artist> artists = new HashMap<>();

    public static final String PLAYLISTS_FILE = "my_playlists.ser";
    public static final String USER_PREFS_FILE = "user_prefs.ser";

    public static void main(String[] args) {

        System.out.println("=== SPOTIFY MUSIC STREAMING PLATFORM ===");
        System.out.println("--- Console Output ---");

        // ------------------------
        // ARTISTS
        // ------------------------
        Artist weeknd = new Artist("ar1", "The Weeknd");
        artists.put(weeknd.getId(), weeknd);

        // ------------------------
        // SONG OBJECTS
        // ------------------------
        Track blinding = new Track("t1", "Blinding Lights", 200, "The Weeknd", "Pop");
        Track saveYourTears = new Track("t2", "Save Your Tears", 215, "The Weeknd", "Pop");

        Podcast techTalk = new Podcast("p1", "Tech Talk Daily", 2700, "Alex Johnson", "Technology");
        Podcast morningNews = new Podcast("p2", "Morning News", 1200, "News Host", "Daily Briefing");

        Audiobook theAlchemist = new Audiobook("b1", "The Alchemist", 4530, "Paulo Coelho", "John Doe");

        // ------------------------
        // LIBRARY
        // ------------------------
        library.put(blinding.getId(), blinding);
        library.put(saveYourTears.getId(), saveYourTears);
        library.put(techTalk.getId(), techTalk);
        library.put(morningNews.getId(), morningNews);
        library.put(theAlchemist.getId(), theAlchemist);

        // Associate songs
        weeknd.addSong(blinding);
        weeknd.addSong(saveYourTears);

        // ------------------------
        // PLAY & DOWNLOAD DEMO
        // ------------------------
        blinding.play();
        queue.add(saveYourTears);

        blinding.download();
        techTalk.play();

        queue.add(morningNews);
        techTalk.download();

        theAlchemist.play();
        queue.add(theAlchemist);
        theAlchemist.download();

        // ------------------------
        // USER
        // ------------------------
        System.out.println("\n--- User Interactions ---");

        User v = new User("u1", "Varnika", "v@example.com");
        Playlist fav = v.createPlaylist("My Favorites 2024");

        fav.addSong(blinding);
        fav.addSong(saveYourTears);
        allPlaylists.add(fav);

        v.likeSong(blinding);
        v.followArtist(weeknd);

        // ------------------------
        // SAVE INFORMATION
        // ------------------------
        System.out.println("--- File Operations ---");
        try {
            FileManager.savePlaylistsAsDTO(allPlaylists, PLAYLISTS_FILE, v);
            System.out.println("💾 Saved playlists.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            FileManager.saveUserPreferences(USER_PREFS_FILE, v);
            System.out.println("📂 Saved user preferences.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ------------------------
        // THREADS
        // ------------------------
        System.out.println("--- Multithreading Output ---");
        AudioStreamer t1 = new AudioStreamer(blinding.getTitle());
        AudioStreamer t2 = new AudioStreamer(techTalk.getTitle());

        t1.start();
        try { t1.join(); } catch (Exception e) {}

        t2.start();
        try { t2.join(); } catch (Exception e) {}

        // ------------------------
        // GUI
        // ------------------------
        System.out.println("\n--- Launching GUI ---");
        SwingUtilities.invokeLater(() -> SpotifyGUI.createAndShowGUI(blinding, allPlaylists, queue));
    }
}
