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
                    "user.name like %:name% order by user.id asc"
    )
    List<User> searchAllByName(
            @RequestParam(name = "name") String name);
}
