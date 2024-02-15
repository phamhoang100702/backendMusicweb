package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.model.request.LoginRequest;
import project.musicwebsite.service.implement.AdminService;

@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    // hoang ok

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        System.out.println("access");

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Success", "ok",
                                adminService.getById(id))
                );
    }

}
