package project.musicwebsite.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.Role;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.model.dto.LoginDTO;
import project.musicwebsite.repositories.CensorRepository;
import project.musicwebsite.repositories.RoleRepository;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.security.UserPrincipal;
import project.musicwebsite.security.jwt.JwtIssuer;
import project.musicwebsite.service.implement.UserService;

@Service
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private SingerRepository singerRepository;
    private CensorRepository censorRepository;

    @Autowired
    public AuthService(JwtIssuer jwtIssuer, AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       SingerRepository singerRepository, CensorRepository censorRepository) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.singerRepository = singerRepository;
        this.censorRepository = censorRepository;
    }

    public LoginDTO attemptLogin(String email, String password){
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principal = (UserPrincipal) authentication.getPrincipal();
            var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userid(principal.getId())
                    .email(principal.getUsername())
                    .roles(principal.getAuthorities().stream().
                            map(GrantedAuthority::getAuthority).toList())
                    .build()
            );
            return LoginDTO.builder()
                    .token(token)
                    .build();
        }catch (Exception e){
            throw  new BadRequestException("Wrong username or password");
        }
    }

    public User userRegister(User user){
        if(userRepository.existsUsersByEmail(user.getEmail())){
            throw new BadRequestException("User is existed");
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Role role = roleRepository.findByName("USER").get();
        user.addRole(role);

        return userRepository.save(user);
    }

    public Singer singerRegister(Singer singer){
        if(userRepository.existsUsersByEmail(singer.getEmail())){
            throw new BadRequestException("User is existed");
        }

        String password = passwordEncoder.encode(singer.getPassword());
        singer.setPassword(password);
        Role role = roleRepository.findByName("SINGER").get();
        Role role1 = roleRepository.findByName("USER").get();
        singer.addRole(role);
        singer.addRole(role1);

        return singerRepository.save(singer);
    }

    public User censorRegister(Censor censor){
        if(userRepository.existsUsersByEmail(censor.getEmail())){
            throw new BadRequestException("User is existed");
        }

        String password = passwordEncoder.encode(censor.getPassword());
        censor.setPassword(password);
        Role role = roleRepository.findByName("USER").get();
        Role role1 = roleRepository.findByName("CENSOR").get();
        censor.addRole(role);
        censor.addRole(role1);

        return censorRepository.save(censor);
    }
}
