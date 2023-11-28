package project.musicwebsite.service.i;

import project.musicwebsite.entity.Playlist;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {
    Playlist save(Playlist playlist);
    Playlist findFavoritePlaylist(Long userId,Long playlistId);

    List<Playlist> getAllForUser();

    List<Playlist> getAll();

    Optional<Playlist> findById(Long id);

    Playlist update(Long id,Playlist playlist);

    void delete();

}
