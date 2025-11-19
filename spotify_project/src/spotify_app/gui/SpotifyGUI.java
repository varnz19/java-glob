package spotify_app.gui;

import spotify_app.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 * Simple GUI matching the user's expected layout (Option C).
 * Place this file at: src/spotify_app/gui/SpotifyGUI.java
 *
 * Uses the uploaded artwork file at: /mnt/data/bf86e486-1a0a-4d82-9cf0-9a135369f3aa.png
 */
public class SpotifyGUI {

    // Use the uploaded image as the album artwork
    private static final String ARTWORK_PATH = "/mnt/data/bf86e486-1a0a-4d82-9cf0-9a135369f3aa.png";

    // Theme colors
    private static final Color BG = new Color(14,14,14);
    private static final Color PANEL = new Color(24,24,24);
    private static final Color SIDEBAR = new Color(18,18,18);
    private static final Color ACCENT = new Color(30,215,96);
    private static final Color TEXT = new Color(230,230,230);
    private static final Color DIM = new Color(160,160,160);

    /**
     * Create and show the GUI.
     * nowPlaying: currently playing Song
     * playlists: list of Playlist objects (first becomes "My Favorites 2024" if present)
     * queue: LinkedList<Song> queue (can be null)
     */
    public static void createAndShowGUI(Song nowPlaying, List<Playlist> playlists, LinkedList<Song> queue) {
        JFrame frame = new JFrame("Spotify Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,650);
        frame.setMinimumSize(new Dimension(820,520));
        frame.getContentPane().setBackground(BG);
        frame.setLayout(new BorderLayout());

        // ---------- LEFT SIDEBAR ----------
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(260, frame.getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12,12,12,12));

        JLabel title = new JLabel("CODECLASH"); // header/title
        title.setForeground(ACCENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton home = makeSidebarButton("Home");
        JButton search = makeSidebarButton("Search");

        JLabel libraryLabel = new JLabel("Your Library");
        libraryLabel.setForeground(DIM);
        libraryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        libraryLabel.setBorder(new EmptyBorder(10,0,6,0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("My Favorites 2024");
        listModel.addElement("Chill Vibes");
        listModel.addElement("Workout Mix");
        listModel.addElement("Recently Played");

        if (playlists != null && playlists.size() > 0) {
            listModel.set(0, playlists.get(0).getName()); // prefer user's first playlist as top element
        }

        JList<String> playlistsJ = new JList<>(listModel);
        playlistsJ.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlistsJ.setBackground(new Color(10,14,16));
        playlistsJ.setForeground(Color.WHITE);
        playlistsJ.setFixedCellHeight(28);
        JScrollPane listScroll = new JScrollPane(playlistsJ);
        listScroll.setBorder(BorderFactory.createEmptyBorder());
        listScroll.setPreferredSize(new Dimension(240, 280));

        sidebar.add(title);
        sidebar.add(Box.createRigidArea(new Dimension(0,12)));
        sidebar.add(home);
        sidebar.add(Box.createRigidArea(new Dimension(0,8)));
        sidebar.add(search);
        sidebar.add(Box.createRigidArea(new Dimension(0,14)));
        sidebar.add(libraryLabel);
        sidebar.add(listScroll);

        // ---------- CENTER (Now Playing) ----------
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(PANEL);
        center.setBorder(new EmptyBorder(18,18,18,18));

        JLabel nowLabel = new JLabel("Now Playing");
        nowLabel.setForeground(TEXT);
        nowLabel.setFont(nowLabel.getFont().deriveFont(Font.BOLD, 16f));
        center.add(nowLabel, BorderLayout.NORTH);

        JPanel nowPanel = new JPanel();
        nowPanel.setBackground(PANEL);
        nowPanel.setLayout(new BoxLayout(nowPanel, BoxLayout.Y_AXIS));
        nowPanel.setBorder(new EmptyBorder(12,12,12,12));

        // artwork
        JLabel artwork = new JLabel();
        artwork.setPreferredSize(new Dimension(300,300));
        artwork.setMaximumSize(new Dimension(350,350));
        artwork.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            File f = new File(ARTWORK_PATH);
            if (f.exists()) {
                BufferedImage img = ImageIO.read(f);
                Image scaled = img.getScaledInstance(300,300,Image.SCALE_SMOOTH);
                artwork.setIcon(new ImageIcon(scaled));
            } else {
                artwork.setText("[ Album artwork ]");
                artwork.setForeground(DIM);
                artwork.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception ex) {
            artwork.setText("[ Album artwork ]");
            artwork.setForeground(DIM);
            artwork.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // song title and artist
        JLabel songTitle = new JLabel("Song title: " + nowPlaying.getTitle());
        songTitle.setForeground(TEXT);
        songTitle.setFont(songTitle.getFont().deriveFont(Font.BOLD, 15f));
        songTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        songTitle.setBorder(new EmptyBorder(8,0,4,0));

        String artistName = (nowPlaying instanceof Track) ? ((Track)nowPlaying).getArtist() : "Various";
        JLabel songArtist = new JLabel("Artist: " + artistName);
        songArtist.setForeground(DIM);
        songArtist.setAlignmentX(Component.CENTER_ALIGNMENT);

        // progress
        JSlider progress = new JSlider(0, nowPlaying.getDuration(), 0);
        progress.setAlignmentX(Component.CENTER_ALIGNMENT);
        progress.setPreferredSize(new Dimension(700, 18));
        JLabel progLabel = new JLabel("0:00 / " + nowPlaying.getFormattedDuration());
        progLabel.setForeground(TEXT);
        progLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        progLabel.setBorder(new EmptyBorder(6,0,0,0));

        nowPanel.add(artwork);
        nowPanel.add(Box.createRigidArea(new Dimension(0,12)));
        nowPanel.add(songTitle);
        nowPanel.add(songArtist);
        nowPanel.add(Box.createRigidArea(new Dimension(0,10)));
        nowPanel.add(progress);
        nowPanel.add(progLabel);

        center.add(nowPanel, BorderLayout.CENTER);

        // ---------- BOTTOM CONTROLS ----------
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(20,20,20));
        bottom.setBorder(new EmptyBorder(8,12,8,12));

        // center controls
        JPanel controls = new JPanel();
        controls.setBackground(bottom.getBackground());
        controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
        controls.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton prev = controlBtn("⏮️");
        JButton play = controlBtn("⏯️");
        JButton next = controlBtn("⏭️");
        JButton shuffle = controlBtn("🔀");
        JButton repeat = controlBtn("🔁");
        JButton like = controlBtn("❤️");

        controls.add(prev);
        controls.add(Box.createRigidArea(new Dimension(8,0)));
        controls.add(play);
        controls.add(Box.createRigidArea(new Dimension(8,0)));
        controls.add(next);
        controls.add(Box.createRigidArea(new Dimension(16,0)));

        // mini progress (visual)
        JSlider mini = new JSlider(0, nowPlaying.getDuration(), 0);
        mini.setPreferredSize(new Dimension(360, 14));
        mini.setOpaque(false);
        controls.add(mini);
        controls.add(Box.createRigidArea(new Dimension(12,0)));
        controls.add(shuffle);
        controls.add(Box.createRigidArea(new Dimension(8,0)));
        controls.add(repeat);

        // right: volume & like
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(bottom.getBackground());
        JLabel volLbl = new JLabel("Vol");
        volLbl.setForeground(DIM);
        volLbl.setBorder(new EmptyBorder(0,0,0,4));
        JSlider vol = new JSlider(0,100,75);
        vol.setPreferredSize(new Dimension(120,14));
        vol.setOpaque(false);
        right.add(volLbl);
        right.add(vol);
        right.add(like);

        bottom.add(controls, BorderLayout.CENTER);
        bottom.add(right, BorderLayout.EAST);

        // ---------- INTERACTIONS ----------
        play.addActionListener(e -> nowPlaying.play());
        next.addActionListener(e -> {
            if (queue != null && !queue.isEmpty()) {
                Song s = queue.removeFirst();
                s.play();
                songTitle.setText("Song title: " + s.getTitle());
                songArtist.setText("Artist: " + (s instanceof Track ? ((Track)s).getArtist() : "Various"));
                progress.setMaximum(s.getDuration());
                progLabel.setText("0:00 / " + s.getFormattedDuration());
                // artwork leaving as-is for simplicity
            } else {
                JOptionPane.showMessageDialog(frame, "Queue is empty");
            }
        });

        // update label when slider moves
        progress.addChangeListener(e -> {
            int v = progress.getValue();
            progLabel.setText(formatTime(v) + " / " + nowPlaying.getFormattedDuration());
        });

        // playlist click
        playlistsJ.addListSelectionListener((ListSelectionListener) e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = playlistsJ.getSelectedValue();
                JOptionPane.showMessageDialog(frame, "Selected playlist: " + sel);
            }
        });

        // ---------- FINAL LAYOUT ----------
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // helper to create sidebar buttons
    private static JButton makeSidebarButton(String text) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(240, 36));
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        b.setOpaque(true);
        return b;
    }

    // control button style
    private static JButton controlBtn(String emoji) {
        JButton b = new JButton(emoji);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setForeground(TEXT);
        b.setFont(b.getFont().deriveFont(16f));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(36,36));
        return b;
    }

    private static String formatTime(int secs) {
        int m = secs / 60;
        int s = secs % 60;
        return String.format("%d:%02d", m, s);
    }
}
