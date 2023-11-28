package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.musicwebsite.entity.Song;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends  JpaRepository<Song,Long> {

    @Query("Select songs from  Song songs where songs.name like ?1")
    List<Song> searchByName(String name);

    @Override
    void delete(Song entity);

    Optional<Song> findByFileLyricOrFileSong(String fileLyric,String fileSong);

    @Query(
            value = "SELECT songs.id from Songtbl songs where songs.albumId=?1",
    nativeQuery = true
    )
    List<Long> findSongsByAlbumId(Long albumId);



    @Query(
            value = "SELECT songs.id from Songtbl songs where songs.singerId=?1",
            nativeQuery = true
    )
    List<Long> findSongBySingerId(Long singerId);

}
