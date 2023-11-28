package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByRole(int i);
    Optional<User> findByEmail(String email);

    @Query(
            value = "select followers.singer_id from Followertbl followers  where followers.user_id=?1",
            nativeQuery = true
    )
    List<Long> findFollowedSingerByUserId(Long user_id);

}
