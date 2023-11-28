package project.musicwebsite.service.i;

import project.musicwebsite.entity.Album;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {
    Album save(Long id,Album album);
    Album update(Long id,Album album);
    Optional<Album> findById(Long id);
    List<Album> getAll();
    List<Album> getBySinger(Long singerId);
    void delete(Long albumId);

}
