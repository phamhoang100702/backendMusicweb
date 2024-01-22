package project.musicwebsite.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.*;
import project.musicwebsite.model.dto.LoginDTO;
import project.musicwebsite.model.request.LoginRequest;
import project.musicwebsite.model.request.Token;
import project.musicwebsite.security.jwt.JwtDecoder;
import project.musicwebsite.service.security.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    private String temp = "";
    private Integer count;


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

    @PostMapping("/decode")
    public ResponseEntity<ResponseObject> decode(@RequestBody Token token) {

        this.temp = token.getToken();
        authService.getInformationByToke(token.getToken());
        count = 0;
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                authService.getInformationByToke(token.getToken())
                                )
                );
    }

    @GetMapping("/temp")
    public ResponseEntity<ResponseObject> getTemp() {

        String result = this.temp;
        count++;
        if(count == 4){
            this.temp = "";
        }


        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("ok", "Success",
                                result
                        )
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
