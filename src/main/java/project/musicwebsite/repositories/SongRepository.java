package project.musicwebsite.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.musicwebsite.entity.Song;
import project.musicwebsite.exception.InternalServerError;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends  JpaRepository<Song,Long> {

    @Query("SELECT s FROM Song s WHERE s.name LIKE %:name%")
    List<Song> searchByName(@Param("name") String name);

    @Override
    void delete(Song entity);

    Optional<Song> findByFileLyricOrFileSound(String fileLyric,String fileSong);

    @Query(
            value = "SELECT songs.id from Songtbl songs where songs.albumId=?1",
    nativeQuery = true
    )
    List<Long> findSongsByAlbumId(Long albumId);



    @Query(
            value = "SELECT other.song_id from song_singer other  where other.singer_id=?1",
            nativeQuery = true
    )
    List<Long> findSongBySingerId(Long singerId);

    @Query(
            value = "SELECT * FROM Songtbl song WHERE name LIKE %:name% OFFSET :offset LIMIT :limit",
            nativeQuery = true
    )
    List<Song> searchAllByName(@Param("name") String name, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT COUNT(*) FROM Song WHERE name LIKE %:name%")
    long countByName(@Param("name") String name);

    default Page<Song> searchAllByNameAsPage(String name,int offset,int limit) {
        // Limit to 10 records
        List<Song> resultList = searchAllByName(name, offset, limit);
        long totalCount = countByName(name);

        return new PageImpl<>(resultList, PageRequest.of(offset/limit, limit), totalCount);
    }
}




