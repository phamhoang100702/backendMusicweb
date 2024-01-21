package project.musicwebsite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.*;
import project.musicwebsite.model.dto.LoginDTO;
import project.musicwebsite.model.request.LoginRequest;
import project.musicwebsite.service.security.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = {"http://localhost:3000/*"})
public class AuthController {
    @Autowired
    AuthService authService;


    @PostMapping("/login/user")
    public ResponseEntity<ResponseObject> login(@RequestBody
                                                LoginRequest loginRequest) {
        System.out.println("access");

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.attemptLogin(loginRequest.getUsername(),
                                        loginRequest.getPassword()))
                );
    }

    @PostMapping("/register/user")
    public ResponseEntity<ResponseObject> userRegister(@RequestBody @Validated
                                                       User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.userRegister(user))
                );
    }

    @PostMapping("/register/singer")
    public ResponseEntity<ResponseObject> singerRegister(@RequestBody @Validated
                                                         Singer singer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.singerRegister(singer))
                );
    }

    @PostMapping("/register/censor")
    public ResponseEntity<ResponseObject> censorRegister(@RequestBody @Validated
                                                         Censor censor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.censorRegister(censor))
                );
    }

    @PostMapping("/login/admin")
    public ResponseEntity<ResponseObject> adminLogin(@RequestBody @Validated
                                                     LoginRequest admin) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.adminLogin(admin.getUsername(), admin.getPassword())
                        )
                );
    }
    @PostMapping("/register/admin")
    public ResponseEntity<ResponseObject> adminRegister(@RequestBody @Validated
                                                     Admin admin) {
        System.out.println("222");
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.adminRegister(admin)
                        )
                );
    }




}
