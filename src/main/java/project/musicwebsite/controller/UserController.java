package project.musicwebsite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.UPremium;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.service.implement.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@Valid @RequestBody User user) {
        User user1 = userService.save(user);
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Saving success", user1)
        );
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        List<User> list = userService.getAll();
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Finding success", list)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<User> user1 = userService.findById(id);
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Finding success", user1)
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@PathVariable Long id, @Valid @RequestBody User user) {
        User user1 = userService.update(id, user);
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Updating success", user1)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(
                new ResponseObject("Ok", "Delete success", "")
        );
    }

    @GetMapping("/{id}/singer")
    ResponseEntity<ResponseObject> findSingerByUserId(@PathVariable Long id) {
        List<Singer> singers = userService.findFollowedSinger(id);
        return ResponseEntity.ok(
                new ResponseObject("Ok", "success", singers)
        );
    }

    @PostMapping("/{id}/premium")
    ResponseEntity<ResponseObject> switchToPremium(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "success", userService.switchToPremium(id))
        );
    }

    @PostMapping("/patch")
        ResponseEntity<ResponseObject> saveListUser(@RequestBody ArrayList<User> list) {
        return ResponseEntity.ok(
                new ResponseObject("Ok", "success", userService.saveListUser(list))
        );
    }

}
