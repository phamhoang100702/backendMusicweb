package project.musicwebsite.service.i;

import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Song;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {
    Playlist save(Long userId,Playlist playlist);
    Playlist findFavoritePlaylist(Long userId);

    List<Playlist> getAllPlaylistByUserId(Long userId);

    List<Playlist> getAll();

    Optional<Playlist> findById(Long id);

    Playlist update(Playlist playlist);

    void delete(Long id);

    Long getTotalPlayList();

    List<Song> getAllSongByPlaylist(Long id);

    List<Playlist> searchAllPlaylistNameForUser(String name);
    List<Playlist> getAllMainpagePlaylist();

}
