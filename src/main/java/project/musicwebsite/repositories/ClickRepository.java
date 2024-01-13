package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Click;
import project.musicwebsite.entity.Song;
import project.musicwebsite.model.dto.ClickDTO;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public interface ClickRepository extends JpaRepository<Click,Long> {

    @Query(
            value="select  count(click.id) from Click click where click.songId=?1 ",
            nativeQuery = true
    )
    Long countAllBySongId(Long songId);

    Long countAllBySong(Song song);

    @Query(
            value = "SELECT COUNT(c.id) AS times, c.song " +
                    "FROM Click c " +
                    "WHERE c.createdDate >= :date and c.song.status = 2 " +
                    "GROUP BY c.song " +
                    "ORDER BY times DESC"
    )
    List<Object[]> countAllClickByDays(@Param("date") Date date);


    @Query(
            value = "SELECT COUNT(c.id) AS times, c.song.creator " +
                    "FROM Click c " +
                    "WHERE c.createdDate >= :date and c.song.status = 2 " +
                    "GROUP BY c.song.creator " +
                    "ORDER BY times DESC"
    )
    List<Object[]> topSinger(@Param("date") Date date);


    @Query(
            value = "Select  COUNT(click.id) AS times, click.song " +
                    "FROM Click click " +
                    "Where click.song.status=2 " +
                    "GROUP BY click.song " +
                    "ORDER BY times DESC "
                     // You can refer to the alias in the ORDER BY clause
    )
    List<Object[]> countAllBySong();


    @Query(
            value="select distinct click.songId from Click click where click.userId=?1 order by click.id desc",
            nativeQuery = true
    )
    List<Long> findAllSongIdByUserId(Long userId);

    @Query(
            value = "Select count(id) as times,createdDate from Click where createdDate >= :date " +
                    "group by createdDate"
    )
    List<Object[]> getChartInforInTimePeriod(@RequestParam(name = "date") Date date);
}
