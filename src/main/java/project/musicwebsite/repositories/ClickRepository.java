package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.musicwebsite.entity.Click;
import project.musicwebsite.entity.Song;

import java.util.List;

public interface ClickRepository extends JpaRepository<Click,Long> {

    @Query(
            value="select count(click.id) from Click click where click.songId=?1 ",
            nativeQuery = true
    )
    Long countAllBySongId(Long songId);

    @Query(
            value="select distinct click.songId from Click click where click.userId=?1 order by click.id desc",
            nativeQuery = true
    )
    List<Long> findAllSongIdByUserId(Long userId);
}
