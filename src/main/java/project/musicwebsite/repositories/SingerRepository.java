package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;

import java.util.List;

public interface SingerRepository extends JpaRepository<Singer,Long> {
    @Query(
           value = "select followers.user_id From Followertbl followers where followers.singer_id=?1",
            nativeQuery = true
    )
    List<Long> findFollowersBySingerId(Long singer_id);

    List<Singer> searchAllByName(String name);
//    List<Singer> searchAllByNameOrNickName(String name,String nickName);
    List<Singer> searchAllByStatus(Boolean status);
    @Query(
            value = "Select singer From Singer singer Where singer.name like %:name% " +
                    "or singer.nickName like %:nickName% "
    )
     List<Singer> searchByNameOrNickName(
             @Param("name") String name,
             @Param("nickName") String nickName);

    @Query(
            value = "Select singer From Singer singer Where (singer.name like %:name% " +
                    "or singer.nickName like %:nickName% ) and singer.status=:status"
    )
    List<Singer> searchByNameOrNickName(
            @Param("name") String name,
            @Param("nickName") String nickName,
            @Param("status") Boolean status);


    @Query(
            value = "select singer from Singer singer" +
                    " where singer.nickName like %:nickName%"
    )
    List<Singer> searchAllByNickName(String nickName);

    @Query(
            value = "select singer from Singer singer" +
                    " where singer.nickName like %:nickName% " +
                    "and singer.status=:status"
    )
    List<Singer> searchAllByNickNameAndStatus(@Param("nickName") String nickName,
                                              @Param("status") Boolean status);
}
