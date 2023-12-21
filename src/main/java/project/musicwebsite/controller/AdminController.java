package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.musicwebsite.service.implement.AdminService;

@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

}
