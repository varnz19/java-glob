package spotify_app.core;

import spotify_app.models.Playlist;
import spotify_app.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static void savePlaylistsAsDTO(List<Playlist> playlists, String filename, User user) throws Exception {

        List<PlaylistDTO> dtoList = new ArrayList<>();

        for (Playlist p : playlists) {
            dtoList.add(new PlaylistDTO(
                    p.getName(),
                    p.getSongTitles()
            ));
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dtoList);
        }
    }

    public static void saveUserPreferences(String filename, User user) throws Exception {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject("Preferred user: " + user.getName());
        }
    }
}
