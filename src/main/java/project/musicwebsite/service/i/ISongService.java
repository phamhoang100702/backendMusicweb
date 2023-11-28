package project.musicwebsite.service.i;

import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Song;

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
    Album createSongWithAlbum(Long idAlbum, Song song);
    Album addExistedSongToAlbum(Album album, Song song);
    Album removeSongFromPlayList(Long idAlbum,Long idSong);
    void delete(Long id);
}
