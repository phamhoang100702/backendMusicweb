package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

    @Query(
            value = "select Top 1 *from Playlisttbl playlist where playlist.creatorId = ?1 ",
            nativeQuery = true
    )
    Optional<Playlist> findFirstByUserId(Long id);
    @Query(
            value="SELECT *From Playlisttbl playlist where playlist.creatorId = ?1",
            nativeQuery = true
    )
    List<Playlist> findPlaylistByUserId(Long id);

    @Query(
            value="SELECT playlist.song_id From SongPlaylisttbl playlist where playlist.playlist_id = ?1",
            nativeQuery = true
    )
    List<Long> findAllSongPlaylist(Long id);

    @Query(
            value = "select playlist from Playlist playlist " +
                    "where  playlist.role='MAINPAGE' and " +
                    "playlist.status=true"
    )
    List<Playlist> getAllMainpagePlaylist();

    @Query(
            value = "select playlist from Playlist playlist " +
                    "where playlist.role='USER' and " +
                    "playlist.name like %:name% and " +
                    "playlist.status=true "
    )
    List<Playlist> searchAllPlaylistByNameForUser(
            @RequestParam(name = "name") String name
    );

    @Query(
            value = "select playlist from Playlist playlist " +
                    "where playlist.role='USER' and " +
                    "playlist.name like %:name%"
    )
    List<Playlist> searchAllPlaylistForAdmin(
            @RequestParam(name = "name") String name
    );




}
