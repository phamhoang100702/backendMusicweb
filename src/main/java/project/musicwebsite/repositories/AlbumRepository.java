package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Singer;

import java.util.List;
@Transactional
public interface AlbumRepository extends JpaRepository<Album,Long> {

    @Query("SELECT album FROM Album album WHERE album.singer=?1")
//@Query("SELECT album FROM Album album WHERE album=?1")
    List<Album> findAlbumBySingerId(Singer Singer);

    @Query("SELECT album FRom Album album where album.name like %?1%")
    List<Album> searchAlbumByNames(String string);

}
