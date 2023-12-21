package project.musicwebsite.service.i;

import project.musicwebsite.entity.*;
import project.musicwebsite.model.dto.SongDTO;

import java.util.List;
import java.util.Optional;

public interface ISongService {
    Song save(Long singerId, Song song);
    List<Song> getAll();
    List<Song> searchByName(String name);
    Optional<Song> findById(Long id);
    Song update(Long id, Song song);
    Playlist saveSongToPlaylist(Long idSong,Long idPlaylist);

    Playlist removeSongFromPlaylist(Long idSong,Long idPlaylist);
    Album addSongToAlbum(Long albumId,Long songId);

    List<SongDTO> findSongBySingerId(Long singerId);
    void delete(Long id);

    Long getTotalSong();

    Song saveSingersToSong(Long songId, List<Singer> singers);
    Song removeSingerFromSong(Long songId, List<Singer> singers);


    Song addCategoryToSong(Long songId, List<Category> categories);

    Song removeCategoryToSong(Long songId, List<Category> categories);

    Song save(Song song);
}
