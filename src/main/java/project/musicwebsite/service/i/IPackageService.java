package project.musicwebsite.service.i;

import project.musicwebsite.entity.PremiumPackage;

import java.util.List;
import java.util.Optional;

public interface IPackageService {
    List<PremiumPackage> getAll();
    PremiumPackage findById(Long id);
    PremiumPackage save(PremiumPackage premiumPackage);
    PremiumPackage update(Long packageId,PremiumPackage premiumPackage);
    void delete(Long packageId);
    Long getTotalPackage();
}
