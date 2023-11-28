package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.musicwebsite.entity.Censor;

public interface CensorRepository extends JpaRepository<Censor,Long> {
}
