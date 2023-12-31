package project.musicwebsite.service.i;

import project.musicwebsite.entity.UPremium;
import project.musicwebsite.entity.User;

import java.util.List;
import java.util.Optional;

public interface IPUserService {

    User switchToNormalUser(Long userId);

    UPremium save(UPremium uPremium);

    List<UPremium> getAll();
    Optional<UPremium> findById(Long id);

    UPremium update(Long id,UPremium uPremium);

    void delete(Long id);

    Long getTotalUser();

    UPremium register(Long pUserId,Long packageId);

    UPremium removePackageFromRegister(Long userId,Long packageId);

//    User removeFrom
}
