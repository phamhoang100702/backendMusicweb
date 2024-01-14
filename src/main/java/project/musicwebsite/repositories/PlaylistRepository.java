package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Playlist;

import java.util.List;
import java.util.Optional;

@Transactional
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

    @Query(
            value = "select *from Playlisttbl playlist where playlist.creatorId = :id and " +
                    "role = 'FAVORITE' limit 1",
            nativeQuery = true
    )
    Optional<Playlist> findFavoritePlaylistForUserByUserId(
            @RequestParam(name = "id") Long id);
    @Query(
            value="SELECT *From Playlisttbl playlist where playlist.creatorId = ?1  and role != 'FAVORITE' " +
                    "and role!='MAINPAGE'",
            nativeQuery = true
    )
    List<Playlist> findPlaylistByUserId(Long id);



    @Query(
            value = "select playlist from Playlist playlist " +
                    "where  playlist.role='MAINPAGE' and " +
                    "playlist.status=true"
    )
    List<Playlist> getAllMainpagePlaylist();

    @Query(
            value = "select playlist from Playlist playlist " +
                    "where playlist.role='USER' and " +
                    "lower(playlist.name) like %:name% and " +
                    "playlist.status=true "
    )
    List<Playlist> searchAllPlaylistByNameForUser(
            @RequestParam(name = "name") String name
    );

    @Query(
            value = "select playlist from Playlist playlist " +
                    "where playlist.role='MAINPAGE' and " +
                    "lower(playlist.name) like %:name% and " +
                    "playlist.status=true "
    )
    List<Playlist> searchAllMainPagePlaylistByName(
            @RequestParam(name = "name") String name
    );

    @Query(
            value = "select playlist from Playlist playlist " +
                    "where playlist.role='USER' and " +
                    "lower(playlist.name) like %:name%"
    )
    List<Playlist> searchAllPlaylistForAdmin(
            @RequestParam(name = "name") String name
    );




}
