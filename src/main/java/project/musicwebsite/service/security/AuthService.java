package project.musicwebsite.service.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.*;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.LoginDTO;
import project.musicwebsite.repositories.*;
import project.musicwebsite.security.UserPrincipal;
import project.musicwebsite.security.jwt.JwtIssuer;

@Service
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private SingerRepository singerRepository;
    private CensorRepository censorRepository;
    private AdminRepository adminRepository;

    @Autowired
    public AuthService(JwtIssuer jwtIssuer, AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       SingerRepository singerRepository, CensorRepository censorRepository,
                       AdminRepository adminRepository) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.singerRepository = singerRepository;
        this.censorRepository = censorRepository;
        this.adminRepository = adminRepository;
    }

    public LoginDTO adminLogin(String email, String password) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    ()->new NotFoundException("This account is not existed")
            );
            System.out.println(email);
            if(user.getRole()!=0 && user.getRole()!=4) throw new BadRequestException("You dont have permission");
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            var principal = (UserPrincipal) authentication.getPrincipal();
            var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userid(principal.getId())
                    .email(principal.getUsername())
                    .roles(principal.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority
                    ).toList()
                    )
                    .build()
            );
            System.out.println(principal.getAuthorities().toString());
            return LoginDTO.builder()
                    .token(token)
                    .userId(principal.getId())
                    .roles(principal.getAuthorities().toString())
                    .build();
        }catch (Exception e){
            throw new BadRequestException("Wrong username or password");
        }
    }

    public LoginDTO attemptLogin(String email, String password) {
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
            System.out.println("id : " + principal.getId());
            System.out.println("role: " + principal.getAuthorities());
            return LoginDTO.builder()
                    .token(token)
                    .userId(principal.getId())
                    .roles(principal.getAuthorities().toString())
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("Wrong username or password");
        }
    }

    public User userRegister(User user) {
        if (userRepository.existsUsersByEmail(user.getEmail())) {
            throw new BadRequestException("User is existed");
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Role role = roleRepository.findByName("USER").get();
        user.addRole(role);

        return userRepository.save(user);
    }

    public Admin adminRegister(Admin admin) {
        System.out.println("ok");
        if (userRepository.existsUsersByEmail(admin.getEmail())) {
            throw new BadRequestException("User is existed");
        }

        String password = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(password);
        Role role = roleRepository.findByName("ADMIN").get();
        admin.addRole(role);

        return adminRepository.save(admin);
    }

    public Singer singerRegister(Singer singer) {
        if (userRepository.existsUsersByEmail(singer.getEmail())) {
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

    public User censorRegister(Censor censor) {
        if (userRepository.existsUsersByEmail(censor.getEmail())) {
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
