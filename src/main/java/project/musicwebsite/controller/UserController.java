package project.musicwebsite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
                new ResponseObject("ok", "Saving success", user1)
        );
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllByName(
            @RequestParam(name = "name",defaultValue = "") String name
    ) {
        List<User> list = userService.searchAllUserByName(name) ;
        return ResponseEntity.ok(
                new ResponseObject("ok", "Finding success", list)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<User> user1 = userService.findById(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "Finding success", user1)
        );
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> update( @Valid @RequestBody User user) {

        User user1 = userService.update( user);
        return ResponseEntity.ok(
                new ResponseObject("ok", "Updating success", user1)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        System.out.println(id);
        userService.delete(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "Delete success", "")
        );
    }

    @GetMapping("/{id}/singer")
    ResponseEntity<ResponseObject> findAllFollowedSingerByUserId(@PathVariable Long id) {
        List<Singer> singers = userService.findFollowedSinger(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "success", singers)
        );
    }



//    @PostMapping("/{id}/premium")
//    ResponseEntity<ResponseObject> switchToPremium(@PathVariable Long id) {
//        return ResponseEntity.ok(
//                new ResponseObject("Ok", "success", userService.switchToPremium(id))
//        );
//    }

    @PostMapping("/patch")
        ResponseEntity<ResponseObject> saveListUser(@RequestBody ArrayList<User> list) {
        return ResponseEntity.ok(
                new ResponseObject("ok", "success", userService.saveListUser(list))
        );
    }


    @GetMapping("/count")
    ResponseEntity<ResponseObject> getTotalUser() {
        return ResponseEntity.ok(
                new ResponseObject("ok", "success", userService.count())
        );
    }

    @GetMapping("/chart/date/{date}")
    ResponseEntity<ResponseObject> countByDate(@PathVariable Long date) {
        return ResponseEntity.ok(
                new ResponseObject("ok", "success", userService.getChartInforInTimePeriod(date))
        );
    }

}
