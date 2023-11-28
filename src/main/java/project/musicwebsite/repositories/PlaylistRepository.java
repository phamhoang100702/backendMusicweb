package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.musicwebsite.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
}
