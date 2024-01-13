package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByRole(int i);
    Optional<User> findByEmail(String email);

    Boolean existsUsersByEmail(String email);

    @Query(
            value = "select followers.singer_id from Followertbl followers  where followers.user_id=?1",
            nativeQuery = true
    )
    List<Long> findFollowedSingerByUserId(Long user_id);
//
//    @Transactional
//    @Modifying
//    @Query(
//            value = "Update Usertbl set role=2 ,startTime=?2 where id=?1",
//            nativeQuery = true
//    )
//    void switchToPremium(Long id, Date date);
    @Query(
            value = "select  user from User user where " +
                    "lower(user.name) like %:name% " +
                    "and user.role=1 " +
                    "order by user.id asc"
    )
    List<User> searchAllByName(
            @RequestParam(name = "name") String name);

//    @Query(
//            value = "select count(ss.song_id) as total , ss.singer_id from song_singer ss " +
//                    "where ss.singer_id=:singer_id order by ",
//            nativeQuery = true
//    )
//    Long countBySingerId(Long singerId);


    @Override
    @Query(
            value = "select count(id) from User " +
                    "where role=1"
    )
    long count();



    @Query(
            value = "Select count(id) as time,createdDate from User where createdDate >= :date " +
                    "group by createdDate"
    )
    List<Object[]> getChartInforInTimePeriod(@RequestParam(name = "date") Date date);
}
