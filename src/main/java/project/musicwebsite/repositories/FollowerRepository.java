package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Follower;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower,Long> {


    @Transactional
    @Modifying
    @Query(
            value = "delete from Followertbl where " +
                    "userId=:userId and singerId=:singerId",
            nativeQuery = true
    )
    void unFollow(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "singerId") Long singerId
    );

    @Query(
            value = "select distinct (user) from Follower where " +
                    " singer=:singer"
    )
    List<User> findAllFollower(
            @RequestParam(name = "singer")Singer singer
    );

    @Query(
            value = "select distinct (singer) from Follower where " +
                    " user=:user"
    )
    List<Singer> findFollowedSinger(
            @RequestParam(name = "user") User user
    );

    @Transactional
    @Modifying
    @Query(
            value = "delete from Follower where singer=:singer"
    )
    void deleteAllBySinger(
            @RequestParam(name = "singer") Singer singer
    );

//    @Query(
//            value = "select count(distinct (user)) as times,singer from Followertn " +
//                    "group by singer " +
//                    "order by times desc "
//    )
//    List<Object[]> getTopSingerFollowers(
//            @RequestParam(name = "limit") Long limit
//    );

}
