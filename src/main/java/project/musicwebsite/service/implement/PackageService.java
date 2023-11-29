package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.PremiumPackage;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.PackageRepository;
import project.musicwebsite.service.i.IPackageService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PackageService implements IPackageService {

    @Autowired
    PackageRepository packageRepository;

    @Override
    public List<PremiumPackage> getAll() {
        List<PremiumPackage> list = packageRepository.findAll();
        if (list.isEmpty()) throw new NoContentException("Dont have any premium package");
        return list;
    }

    @Override
    public PremiumPackage findById(Long id) {
        Optional<PremiumPackage> premiumPackage = packageRepository.findById(id);
        if (premiumPackage.isEmpty()) throw new NotFoundException("This package is not exist");
        return premiumPackage.get();
    }

    @Override
    public PremiumPackage save(PremiumPackage premiumPackage) {
        return packageRepository.save(premiumPackage);
    }

    @Override
    public PremiumPackage update(Long packageId, PremiumPackage premiumPackage) {

        return packageRepository.findById(packageId)
                .map(premiumPackage1 -> {
                    premiumPackage1.setName(premiumPackage.getName());
                    premiumPackage1.setCost(premiumPackage.getCost());
                    premiumPackage1.setDuration(premiumPackage.getDuration());
                    premiumPackage1.setModifiedDate(new Date());
                    return packageRepository.save(premiumPackage1);
                }).orElseThrow(()->new NotFoundException("This package is not exist"));
    }

    @Override
    public void delete(Long packageId) {
        Optional<PremiumPackage> premiumPackage = packageRepository.findById(packageId);
        if(premiumPackage.isEmpty()) throw new NotFoundException("This package is not exist");
        packageRepository.deleteById(packageId);
    }

    @Override
    public Long getTotalPackage() {

        return packageRepository.count();
    }
}
