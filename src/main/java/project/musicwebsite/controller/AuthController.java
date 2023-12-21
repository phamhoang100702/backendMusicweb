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

    @PostMapping("/user/login")
    public ResponseEntity<ResponseObject> login(@RequestBody
                                                LoginRequest loginRequest) {
        System.out.println("access");

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                authService.attemptLogin(loginRequest.getUsername(),
                                        loginRequest.getPassword()))
                );
    }

    @PostMapping("/user/register")
    public ResponseEntity<ResponseObject> userRegister(@RequestBody @Validated
                                                       User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                authService.userRegister(user))
                );
    }

    @PostMapping("/singer/register")
    public ResponseEntity<ResponseObject> singerRegister(@RequestBody @Validated
                                                         Singer singer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                authService.singerRegister(singer))
                );
    }

    @PostMapping("/censor/register")
    public ResponseEntity<ResponseObject> singerRegister(@RequestBody @Validated
                                                         Censor censor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                authService.censorRegister(censor))
                );
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ResponseObject> adminLogin(@RequestBody @Validated
                                                     LoginRequest admin) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                authService.adminLogin(admin.getUsername(), admin.getPassword())
                        )
                );
    }
    @PostMapping("/admin/register")
    public ResponseEntity<ResponseObject> adminRegister(@RequestBody @Validated
                                                     Admin admin) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                authService.adminRegister(admin)
                        )
                );
    }




}
