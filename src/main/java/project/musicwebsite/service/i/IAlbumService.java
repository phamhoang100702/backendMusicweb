package project.musicwebsite.service.i;

import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Song;
import project.musicwebsite.model.dto.AlbumDTO;
import project.musicwebsite.model.dto.SongDTO;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {
    Album save( Album album);
    Album update(Album album);
    Album findById(Long id);
    List<Album> getAll();
    List<AlbumDTO> getBySinger(Long singerId);
    void delete(Long albumId);

    List<Album> getByName(String string);

    List<SongDTO> getSongByAlbumId(Long albumId);

    void removeSongFromAlbum(Long albumId,Long songId);

    Long count();

    Long countBySingerId();

}
