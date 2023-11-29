package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


}
