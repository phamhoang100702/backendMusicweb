package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.musicwebsite.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmail(String email);

}
