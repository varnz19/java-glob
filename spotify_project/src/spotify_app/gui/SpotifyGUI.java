
package spotify_app.gui;



import spotify_app.models.*;



import javax.imageio.ImageIO;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import javax.swing.event.ChangeEvent;

import javax.swing.event.ListSelectionListener;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import java.io.File;

import java.util.LinkedList;

import java.util.List;



/**

 * Modern, clean Spotify-like GUI (Option A).

 * Drop-in replacement: src/spotify_app/gui/SpotifyGUI.java

 *

 * Uses artwork file path: /mnt/data/bf86e486-1a0a-4d82-9cf0-9a135369f3aa.png

 */

public class SpotifyGUI {



    private static final String ARTWORK_PATH = "/mnt/data/bf86e486-1a0a-4d82-9cf0-9a135369f3aa.png";



    private static final Color BG = new Color(14,14,14);

    private static final Color PANEL = new Color(24,24,24);

    private static final Color SIDEBAR = new Color(18,18,18);

    private static final Color ACCENT = new Color(30,215,96);

    private static final Color TEXT = new Color(230,230,230);

    private static final Color DIM = new Color(160,160,160);



    public static void createAndShowGUI(Song nowPlaying, List<Playlist> playlists, LinkedList<Song> queue) {

        if (nowPlaying == null) throw new IllegalArgumentException("nowPlaying cannot be null");



        JFrame frame = new JFrame("Spotify Music Player");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1100, 660);

        frame.setMinimumSize(new Dimension(880, 540));

        frame.getContentPane().setBackground(BG);

        frame.setLayout(new BorderLayout());



        // ---------- LEFT SIDEBAR ----------

        JPanel sidebar = new JPanel();

        sidebar.setBackground(SIDEBAR);

        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.setBorder(new EmptyBorder(18, 18, 18, 18));

        sidebar.setPreferredSize(new Dimension(260, frame.getHeight()));



        JLabel brand = new JLabel("Spotify");

        brand.setForeground(ACCENT);

        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 20f));

        brand.setAlignmentX(Component.LEFT_ALIGNMENT);



        JButton home = makeSidebarButton("Home");

        JButton search = makeSidebarButton("Search");



        JLabel libraryLabel = new JLabel("Your Library");

        libraryLabel.setForeground(DIM);

        libraryLabel.setBorder(new EmptyBorder(14,0,8,0));

        libraryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);



        DefaultListModel<String> model = new DefaultListModel<>();

        model.addElement("My Favorites 2024");

        model.addElement("Chill Vibes");

        model.addElement("Workout Mix");

        model.addElement("Recently Played");

        if (playlists != null && playlists.size() > 0) model.set(0, playlists.get(0).getName());



        JList<String> playlistList = new JList<>(model);

        playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        playlistList.setBackground(new Color(10,14,16));

        playlistList.setForeground(Color.WHITE);

        playlistList.setFixedCellHeight(30);

        JScrollPane playlistScroll = new JScrollPane(playlistList);

        playlistScroll.setBorder(BorderFactory.createEmptyBorder());

        playlistScroll.setPreferredSize(new Dimension(240, 300));



        sidebar.add(brand);

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        sidebar.add(home);

        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(search);

        sidebar.add(Box.createRigidArea(new Dimension(0, 18)));

        sidebar.add(libraryLabel);

        sidebar.add(playlistScroll);



        // ---------- CENTER: Now Playing ----------

        JPanel center = new JPanel(new BorderLayout());

        center.setBackground(PANEL);

        center.setBorder(new EmptyBorder(20, 20, 20, 20));



        JLabel nowLabel = new JLabel("Now Playing");

        nowLabel.setForeground(TEXT);

        nowLabel.setFont(nowLabel.getFont().deriveFont(Font.BOLD, 16f));

        center.add(nowLabel, BorderLayout.NORTH);



        JPanel nowPanel = new JPanel();

        nowPanel.setBackground(PANEL);

        nowPanel.setLayout(new BoxLayout(nowPanel, BoxLayout.Y_AXIS));

        nowPanel.setBorder(new EmptyBorder(18, 18, 18, 18));



        // Artwork (try load, else placeholder)

        JLabel artworkLabel = new JLabel();

        artworkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        artworkLabel.setPreferredSize(new Dimension(320, 320));

        artworkLabel.setMaximumSize(new Dimension(360, 360));

        artworkLabel.setBorder(BorderFactory.createLineBorder(new Color(60,60,60)));



        try {

            File f = new File(ARTWORK_PATH);

            if (f.exists()) {

                BufferedImage img = ImageIO.read(f);

                Image scaled = img.getScaledInstance(320, 320, Image.SCALE_SMOOTH);

                artworkLabel.setIcon(new ImageIcon(scaled));

            } else {

                artworkLabel.setText("[ Album artwork ]");

                artworkLabel.setForeground(DIM);

                artworkLabel.setHorizontalAlignment(SwingConstants.CENTER);

                artworkLabel.setPreferredSize(new Dimension(320,320));

            }

        } catch (Exception ex) {

            artworkLabel.setText("[ Album artwork ]");

            artworkLabel.setForeground(DIM);

            artworkLabel.setHorizontalAlignment(SwingConstants.CENTER);

            artworkLabel.setPreferredSize(new Dimension(320,320));

        }



        JLabel title = new JLabel(nowPlaying.getTitle());

        title.setForeground(TEXT);

        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        title.setBorder(new EmptyBorder(12, 0, 2, 0));



        String artistText = (nowPlaying instanceof Track) ? ((Track) nowPlaying).getArtist() : "Various";

        JLabel artist = new JLabel(artistText);

        artist.setForeground(DIM);

        artist.setAlignmentX(Component.CENTER_ALIGNMENT);



        JSlider progress = new JSlider(0, nowPlaying.getDuration(), 0);

        progress.setAlignmentX(Component.CENTER_ALIGNMENT);

        progress.setPreferredSize(new Dimension(680, 16));

        progress.setOpaque(false);



        JLabel progLabel = new JLabel("0:00 / " + nowPlaying.getFormattedDuration());

        progLabel.setForeground(TEXT);

        progLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        progLabel.setBorder(new EmptyBorder(8,0,0,0));



        nowPanel.add(artworkLabel);

        nowPanel.add(Box.createRigidArea(new Dimension(0,12)));

        nowPanel.add(title);

        nowPanel.add(artist);

        nowPanel.add(Box.createRigidArea(new Dimension(0,14)));

        nowPanel.add(progress);

        nowPanel.add(progLabel);



        center.add(nowPanel, BorderLayout.CENTER);



        // ---------- BOTTOM PLAYER BAR ----------

        JPanel bottomBar = new JPanel(new BorderLayout());

        bottomBar.setBackground(new Color(20,20,20));

        bottomBar.setBorder(new EmptyBorder(10, 14, 10, 14));



        // center controls (prev/play/next + progress + shuffle/repeat)

        JPanel centerControls = new JPanel();

        centerControls.setOpaque(false);

        centerControls.setLayout(new BoxLayout(centerControls, BoxLayout.X_AXIS));

        centerControls.setBorder(new EmptyBorder(0, 0, 0, 0));



        JButton prevBtn = controlBtn("⏮️");

        JButton playBtn = controlBtn("⏯️");

        JButton nextBtn = controlBtn("⏭️");

        JButton shuffleBtn = controlBtn("🔀");

        JButton repeatBtn = controlBtn("🔁");



        centerControls.add(prevBtn);

        centerControls.add(Box.createRigidArea(new Dimension(10,0)));

        centerControls.add(playBtn);

        centerControls.add(Box.createRigidArea(new Dimension(10,0)));

        centerControls.add(nextBtn);

        centerControls.add(Box.createRigidArea(new Dimension(20,0)));



        // a compact progress group with time labels

        JPanel progressGroup = new JPanel(new BorderLayout());

        progressGroup.setOpaque(false);

        JPanel times = new JPanel(new BorderLayout());

        times.setOpaque(false);

        JLabel startTime = new JLabel("0:00");

        startTime.setForeground(DIM);

        JLabel endTime = new JLabel(nowPlaying.getFormattedDuration());

        endTime.setForeground(DIM);

        progressGroup.add(progress, BorderLayout.CENTER);



        JPanel timePanel = new JPanel(new BorderLayout());

        timePanel.setOpaque(false);

        timePanel.add(startTime, BorderLayout.WEST);

        timePanel.add(endTime, BorderLayout.EAST);

        progressGroup.add(timePanel, BorderLayout.SOUTH);



        centerControls.add(progressGroup);

        centerControls.add(Box.createRigidArea(new Dimension(12,0)));

        centerControls.add(shuffleBtn);

        centerControls.add(Box.createRigidArea(new Dimension(8,0)));

        centerControls.add(repeatBtn);



        bottomBar.add(centerControls, BorderLayout.CENTER);



        // right controls (volume + like)

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        rightControls.setOpaque(false);

        JLabel volIcon = new JLabel("🔊");

        volIcon.setForeground(TEXT);

        JSlider volume = new JSlider(0, 100, 75);

        volume.setPreferredSize(new Dimension(120, 14));

        volume.setOpaque(false);

        JButton likeBtn = roundControlBtn("❤️");



        rightControls.add(volIcon);

        rightControls.add(volume);

        rightControls.add(likeBtn);



        bottomBar.add(rightControls, BorderLayout.EAST);



        // ---------- INTERACTIONS ----------

        playBtn.addActionListener(e -> nowPlaying.play());



        nextBtn.addActionListener(e -> {

            if (queue != null && !queue.isEmpty()) {

                Song s = queue.removeFirst();

                s.play();

                // update UI

                title.setText(s.getTitle());

                artist.setText((s instanceof Track) ? ((Track)s).getArtist() : "Various");

                progress.setMaximum(s.getDuration());

                endTime.setText(s.getFormattedDuration());

                progLabel.setText("0:00 / " + s.getFormattedDuration());

                // attempt to load artwork kept same (or replace if you provide per-song art)

            } else {

                JOptionPane.showMessageDialog(frame, "Queue is empty");

            }

        });



        progress.addChangeListener((ChangeEvent e) -> {

            int v = progress.getValue();

            startTime.setText(formatTime(v));

            progLabel.setText(formatTime(v) + " / " + nowPlaying.getFormattedDuration());

        });



        playlistList.addListSelectionListener((ListSelectionListener) e -> {

            if (!e.getValueIsAdjusting()) {

                String sel = playlistList.getSelectedValue();

                JOptionPane.showMessageDialog(frame, "Selected playlist: " + sel);

            }

        });



        // Final assembly

        frame.add(sidebar, BorderLayout.WEST);

        frame.add(center, BorderLayout.CENTER);

        frame.add(bottomBar, BorderLayout.SOUTH);



        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }



    // helper: sidebar button

    private static JButton makeSidebarButton(String text) {

        JButton b = new JButton(text);

        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.setMaximumSize(new Dimension(240, 36));

        b.setBackground(ACCENT);

        b.setForeground(Color.WHITE);

        b.setFocusPainted(false);

        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        b.setOpaque(true);

        return b;

    }



    // helper: control button (plain)

    private static JButton controlBtn(String emoji) {

        JButton b = new JButton(emoji);

        b.setFocusPainted(false);

        b.setBorderPainted(false);

        b.setContentAreaFilled(false);

        b.setForeground(TEXT);

        b.setFont(b.getFont().deriveFont(16f));

        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.setPreferredSize(new Dimension(36, 36));

        return b;

    }



    // helper: rounded control (for like)

    private static JButton roundControlBtn(String emoji) {

        JButton b = new JButton(emoji);

        b.setFocusPainted(false);

        b.setForeground(Color.WHITE);

        b.setBackground(new Color(40,40,40));

        b.setOpaque(true);

        b.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));

        return b;

    }



    private static String formatTime(int secs) {

        int m = secs / 60;

        int s = secs % 60;

        return String.format("%d:%02d", m, s);

    }

}