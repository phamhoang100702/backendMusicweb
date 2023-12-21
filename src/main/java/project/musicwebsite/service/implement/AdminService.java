package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Admin;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.repositories.AdminRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IAdminController;

@Service
public class AdminService implements IAdminController {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Admin save(Admin admin) {
        if(userRepository.existsUsersByEmail(admin.getEmail())){
            throw new BadRequestException("This email is existed");
        }
        return adminRepository.save(admin);
    }

    @Override
    public boolean isExisted(String email) {
        return adminRepository.existsByEmail(email);
    }
}
