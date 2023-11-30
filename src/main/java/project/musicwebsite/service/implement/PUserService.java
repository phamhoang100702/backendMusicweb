package project.musicwebsite.service.implement;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.UPremium;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.PUserRepository;
import project.musicwebsite.repositories.PackageRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IPUserService;

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

    @Override
    public UPremium switchPremium(Long userId) {
        User user1 = userRepository.findById(userId)
                .map(user -> {
                    if (user.getRole() != 1) throw new NotFoundException("Cant create");
                    user.setRole(2);
                    return userRepository.save(user);
                }).orElseThrow(() -> new NotFoundException("This user is not existed"));
        UPremium uPremium = new UPremium();
        uPremium.setId(userId);
        uPremium.setEmail(user1.getEmail());
        uPremium.setPassword(user1.getPassword());
        return uPremium;
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
        Optional<UPremium> uPremium = pUserRepository.findById(pUserId);
        if (uPremium.isEmpty()) throw new NotFoundException("This user is not exist!");
        packageRepository.findById(packageId)
                .map(premiumPackage -> {
                    premiumPackage.addUser(uPremium.get());
                    uPremium.map(
                            uPremium1 -> {
                                // xu ly
                                return pUserRepository.save(uPremium1);
                            }
                    );
                    return packageRepository.save(premiumPackage);
                }).orElseThrow(() -> new NotFoundException("This package is not exist"));
        return pUserRepository.findById(pUserId).get();
    }
}
