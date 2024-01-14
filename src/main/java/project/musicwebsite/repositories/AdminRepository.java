package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import project.musicwebsite.entity.Admin;
@Transactional
public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmail(String email);

}
