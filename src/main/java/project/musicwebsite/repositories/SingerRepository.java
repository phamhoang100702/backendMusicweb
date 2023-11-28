package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;

import java.util.List;

public interface SingerRepository extends JpaRepository<Singer,Long> {
    @Query(
           value = "select followers.user_id From Followertbl followers where followers.singer_id=?1",
            nativeQuery = true
    )
    List<Long> findFollowersBySingerId(Long singer_id);
}
