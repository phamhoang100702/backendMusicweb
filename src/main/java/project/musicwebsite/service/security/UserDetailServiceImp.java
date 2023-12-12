package project.musicwebsite.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.security.UserPrincipal;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new NotFoundException("This user is not exist"));

        return UserPrincipal.build(user);
    }
}
