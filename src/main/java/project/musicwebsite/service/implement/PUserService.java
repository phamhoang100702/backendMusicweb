package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.PremiumPackage;
import project.musicwebsite.entity.UPremium;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.PUserRepository;
import project.musicwebsite.repositories.PackageRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IPUserService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PUserService implements IPUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PUserRepository pUserRepository;
    @Autowired
    PackageRepository packageRepository;
//    @Autowired
//    EntityManager entityManager;

    @Override
    public User switchToNormalUser(Long userId) {
        Optional<UPremium> premium = pUserRepository.findById(userId);
        if (premium.isEmpty()) throw new NotFoundException("This user is not exist");
        pUserRepository.switchToNormalUser(userId);
        pUserRepository.deleteAllRegisterByUserId(userId);
//        entityManager.clear();
        return userRepository.findById(userId).get();
    }

    @Override
    public UPremium save(UPremium uPremium) {
        Optional<User> user = userRepository.findByEmail(uPremium.getEmail());
        if (user.isPresent()) throw new BadRequestException("This email is existed");
        return pUserRepository.save(uPremium);
    }

    @Override
    public List<UPremium> getAll() {
        List<UPremium> premiums = pUserRepository.findAll();
        if (premiums.isEmpty()) throw new NoContentException("Dont have any premium user!");
        return premiums;
    }

    @Override
    public Optional<UPremium> findById(Long id) {
        Optional<UPremium> uPremium = pUserRepository.findById(id);
        if (uPremium.isEmpty()) throw new NotFoundException("This user is not existed");
        return uPremium;
    }

    @Override
    public UPremium update(Long id, UPremium uPremium) {
        return pUserRepository.findById(id)
                .map(user -> {
                    user.setBirth(uPremium.getBirth());
                    user.setHobby(uPremium.getHobby());
                    user.setStartTime(uPremium.getStartTime());
                    user.setEndTime(uPremium.getEndTime());
                    return pUserRepository.save(user);
                }).orElseThrow(() -> new NotFoundException("This user is not existed"));
    }

    @Override
    public void delete(Long id) {
        Optional<UPremium> uPremium = pUserRepository.findById(id);
        if (uPremium.isEmpty()) throw new NotFoundException("This user is not existed");
        pUserRepository.deleteById(id);
    }

    @Override
    public Long getTotalUser() {
        return pUserRepository.count();
    }

    @Override
    public UPremium register(Long pUserId, Long packageId) {

        Optional<PremiumPackage> package1 = packageRepository.findById(packageId);
        if (package1.isEmpty()) throw new NotFoundException("This package is not exist");

        System.out.println("ok");
        return pUserRepository.findById(pUserId)
                .map(
                        uPremium1 -> {
                            System.out.println(1);
                            uPremium1.addPackage(package1.get());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(uPremium1.getStartTime());
                            if (uPremium1.getEndTime() != null) calendar.setTime(uPremium1.getEndTime());
                            calendar.add(Calendar.MONTH, package1.get().getDuration());
                            Date date1 = calendar.getTime();
                            System.out.println("ijjjj");
                            uPremium1.setEndTime(date1);
                            return pUserRepository.save(uPremium1);
                        }
                ).orElseThrow(()->new NotFoundException("This user is not exist"));
    }

    @Override
    public UPremium removePackageFromRegister(Long userId, Long packageId) {
        Optional<PremiumPackage> package1 = packageRepository.findById(packageId);
        if (package1.isEmpty()) throw new NotFoundException("This package is not exist");

        return pUserRepository.findById(userId)
                .map(
                        uPremium1 -> {
                            uPremium1.removePackage(package1.get());
                            if(uPremium1.getPackages().isEmpty()) throw new NotFoundException("You have not registed yet");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(uPremium1.getStartTime());
                            if (uPremium1.getEndTime() != null) calendar.setTime(uPremium1.getEndTime());
                            calendar.add(Calendar.MONTH, -package1.get().getDuration());
                            Date date1 = calendar.getTime();
                            System.out.println(date1);
                            uPremium1.setEndTime(date1);
                            return pUserRepository.save(uPremium1);
                        }
                ).orElseThrow(()->new NotFoundException("This user is not exist"));

    }
}
