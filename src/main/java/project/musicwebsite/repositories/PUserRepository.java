package project.musicwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.musicwebsite.entity.PremiumPackage;
import project.musicwebsite.entity.UPremium;
import project.musicwebsite.service.implement.PUserService;

public interface PUserRepository extends JpaRepository<UPremium,Long> {
}
