package project.musicwebsite.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Category;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.Song;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE lower(s.name) LIKE %:name%")
    List<Song> searchByName(@Param("name") String name);

    @Query("SELECT s FROM Song s WHERE lower(s.name) LIKE %:name% and s.status=:status")
    List<Song> searchByName(@Param("name") String name,
                            @Param("status") Integer status);

    List<Song> searchAllByNameOrCategoriesContainingOrSingersContaining(
            String name,Category category,Singer singer
    );
    @Override
    void delete(Song entity);

    Optional<Song> findByFileLyricOrFileSound(String fileLyric, String fileSong);

    @Query(
            value = "SELECT songs.id from Songtbl songs where songs.albumId=?1",
            nativeQuery = true
    )
    List<Long> findSongsByAlbumId(Long albumId);


    @Query(
            value = "SELECT * from songtbl other  where other.creatorid=?1",
            nativeQuery = true
    )
    List<Song> findSongByCreatorId(Long singerId);

    @Query(
            value = "SELECT * FROM Songtbl song WHERE LOWER(name) LIKE %:name% OFFSET :offset LIMIT :limit",
            nativeQuery = true
    )
    List<Song> searchAllByName(@Param("name") String name, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT COUNT(*) FROM Song WHERE lower(name) LIKE %:name%")
    long countByName(@Param("name") String name);

    default Page<Song> searchAllByNameAsPage(String name, int offset, int limit) {
        // Limit to 10 records
        List<Song> resultList = searchAllByName(name, offset, limit);

        long totalCount = countByName(name);

        return new PageImpl<>(resultList, PageRequest.of(offset / limit, limit), totalCount);
    }



    List<Song>  findAllBySingersContaining(Singer singer);

    List<Song> findAllByCategoriesContaining(Category category);


    @Query(
            value = "Select count(id) as time,FORMAT(createdDate, 'dd/MM/yyyy') AS datecount from Song where createdDate >= :date " +
                    "group by datecount"
    )
    List<Object[]> getChartInforInTimePeriod(@RequestParam(name = "date") Date date);


    @Query(
            value = " select sum(times) as listensOfCategory,category_id from" +
                    "(select song.id as songId,sc.category_id,listens.times " +
                    "from Songtbl song,song_category sc,Category category, " +
                    "(select count(id) as times,songid from click group by songid order by times desc) as listens " +
                    "where song.id = sc.song_id and category.id = sc.category_id and listens.songid = song.id and song.status=2) " +
                    "group by category_id order by listensOfCategory desc limit :limit",
            nativeQuery = true
    )
    List<Object[]> getTopCategoryWithMostListens(
            @RequestParam(name = "limit") Integer limit
    );

    @Query(
            value = "select count(click.id) as times,click.songid " +
                    "from click click,song_category sc " +
                    "where sc.song_id = click.songid and sc.category_id = :category_id " +
                    "group by songid order by times desc limit :limit",
            nativeQuery = true
    )
    List<Object[]> getTopSongWithTheMostListensByCategoryId(
            @RequestParam(name = "category_id")Long category_id,
            @RequestParam(name = "limit") Integer limit
    );




}




