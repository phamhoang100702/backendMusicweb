package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.SongOfPlaylist;

import java.util.List;

public interface SongOfPlaylistRepository extends JpaRepository<SongOfPlaylist,Long> {


    @Transactional
    @Modifying
    @Query(
            value = "delete from SongOfPlaylisttbl where songId=:songId " +
                    "and playlistId = :playlistId ",
            nativeQuery = true
    )
    void deletePlaylistBySongIdAndPlaylistId(
            @RequestParam(name = "songId") Long songId,
            @RequestParam(name = "playlistId") Long playlistId
    );

    @Query(
            value = "select  songId from SongOfPlaylisttbl where playlistId=:id",
            nativeQuery = true
    )
    List<Long> findAllSongPlaylist(
            @RequestParam(name = "id") Long id);
}
