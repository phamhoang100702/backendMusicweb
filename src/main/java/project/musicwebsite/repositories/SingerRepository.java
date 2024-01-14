package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Singer;

import java.util.Date;
import java.util.List;

@Transactional
public interface SingerRepository extends JpaRepository<Singer, Long> {
    @Query(
            value = "select followers.user_id From Followertbl followers where followers.singer_id=?1",
            nativeQuery = true
    )
    List<Long> findFollowersBySingerId(Long singer_id);

    List<Singer> searchAllByName(String name);

    //    List<Singer> searchAllByNameOrNickName(String name,String nickName);
    List<Singer> searchAllByStatus(Boolean status);

    @Query(
            value = "Select singer From Singer singer Where lower( singer.name) like %:name% " +
                    "or lower(singer.nickName) like %:nickName% "
    )
    List<Singer> searchByNameOrNickName(
            @Param("name") String name,
            @Param("nickName") String nickName);

    @Query(
            value = "Select singer From Singer singer Where (lower( singer.name) like %:name% " +
                    "or lower(singer.nickName) like %:nickName% ) and singer.status=:status"
    )
    List<Singer> searchByNameOrNickName(
            @Param("name") String name,
            @Param("nickName") String nickName,
            @Param("status") Boolean status);


    @Query(
            value = "select singer from Singer singer" +
                    " where lower(singer.nickName) like %:nickName%"
    )
    List<Singer> searchAllByNickName(String nickName);

    @Query(
            value = "select singer from Singer singer" +
                    " where lower(singer.nickName)like %:nickName% " +
                    "and singer.status=:status"
    )
    List<Singer> searchAllByNickNameAndStatus(@Param("nickName") String nickName,
                                              @Param("status") Boolean status);

    @Query(
            value = "select  count(fl.user_id) as followers,singer.id,singer.name,singer.nickname,singer.avatar " +
                    " from Followertbl fl,usertbl singer" +
                    " where singer.id = fl.singer_id and singer.status=true " +
                    " group by  singer.id order by followers desc " +
                    "limit :top ",
            nativeQuery = true
    )
    List<Object[]> searchTopTenSingerByFollower(
            @Param("top") Long top
    );

    @Query(
            value = "Select count(id) as time,FORMAT(createdDate, 'dd/MM/yyyy') AS datecount from Singer where createdDate >= :date " +
                    "group by datecount"
    )
    List<Object[]> getChartInforInTimePeriod(@RequestParam(name = "date") Date date);

    @Transactional
    @Modifying
    @Query(
            value = "Delete from singersofsong where singer_id=:singerId"
            ,nativeQuery = true
    )
    void deleteAllSingerOfSongBySingerId(
            @RequestParam(name = "singerId") Long singerId
    );
}
