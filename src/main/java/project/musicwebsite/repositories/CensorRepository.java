package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import project.musicwebsite.entity.Censor;

import java.util.List;

public interface CensorRepository extends JpaRepository<Censor,Long> {


    @Query(
            value = "SELECT  censor from Censor censor where " +
                    "lower(censor.name) like %:name% order by censor.id"
    )
    List<Censor> searchAllByName(@RequestParam(name = "name") String name);
}
