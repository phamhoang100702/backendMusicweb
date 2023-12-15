package project.musicwebsite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.model.dto.LoginDTO;
import project.musicwebsite.model.request.LoginRequest;
import project.musicwebsite.service.security.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody
                                          LoginRequest loginRequest){
        System.out.println("access");

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new ResponseObject("Success","ok",
                            authService.attemptLogin(loginRequest.getUsername(),
                                    loginRequest.getPassword()))
                );
    }

    @PostMapping("/user/register")
    public ResponseEntity<ResponseObject> userRegister(@RequestBody @Validated
                                                   User user){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success","ok",
                                authService.userRegister(user))
                );
    }

    @PostMapping("/singer/register")
    public ResponseEntity<ResponseObject> singerRegister(@RequestBody @Validated
                                                         Singer singer){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success","ok",
                                authService.singerRegister(singer))
                );
    }

    @PostMapping("/censor/register")
    public ResponseEntity<ResponseObject> singerRegister(@RequestBody @Validated
                                                         Censor censor){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success","ok",
                                authService.censorRegister(censor))
                );
    }




}
