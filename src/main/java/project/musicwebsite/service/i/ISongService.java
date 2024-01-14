package project.musicwebsite.service.i;

import project.musicwebsite.entity.*;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.SongDTO;

import java.util.List;
import java.util.Optional;

public interface ISongService {
    Song save(Long singerId, Song song);
    List<Song> getAll();
    List<Song> searchByName(String name);
    Optional<Song> findById(Long id);
    Song update( Song song);
    Playlist saveSongToPlaylist(Long idSong,Long idPlaylist);
    Playlist removeSongFromPlaylist(Long idSong,Long idPlaylist);
    Album addSongToAlbum(Long albumId,Long songId);
    List<Song> findSongByCreatorId(Long singerId);
    void delete(Long id);

    Long getTotalSong();
    Long countSongBySingerId(Long singerId);



    Song save(Song song);

    List<Song> getSongsByCategoryId(Long categoryId);

    List<Song> getSongsBySingerId(Long singerId);


    List<Song> findAllSongByNameAndCategoryAndSinger(
            String name,
            Long  categoryId,
            Long singerId
    );


}
