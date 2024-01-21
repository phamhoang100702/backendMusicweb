package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Click;
import project.musicwebsite.entity.PremiumPackage;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Song;
import project.musicwebsite.service.implement.ClickService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/click")
public class ClickController {

    @Autowired
    ClickService clickService;

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@RequestBody Click click) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.save(click))
        );
    }

    @GetMapping("/day")
    ResponseEntity<ResponseObject> countListensInDay() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.countListensInDay())
        );
    }

    @GetMapping("/month")
    ResponseEntity<ResponseObject> countListensInMonth() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.countListensInMonth())
        );
    }

    @GetMapping("/week")
    ResponseEntity<ResponseObject> countListensInWeek() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.countListensInWeek())
        );
    }


    @GetMapping("/all")
    ResponseEntity<ResponseObject> countListens() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.countListens())
        );
    }

    @GetMapping("/")
    ResponseEntity<ResponseObject> countListensBySong(@RequestBody
                                                      Song song) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.countListensBySong(song))
        );
    }

    @PostMapping("/patch")
    ResponseEntity<ResponseObject> addPatch(@RequestBody
                                            List<Click> clicks ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.addPatch(clicks))
        );
    }

    @GetMapping("/count")
    ResponseEntity<ResponseObject> getTotalListens() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        clickService.count())
        );
    }

    @GetMapping("/history/user/{userId}")
    ResponseEntity<ResponseObject> getHistorySongByUserId(@PathVariable Long userId) {
        System.out.println(userId);
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        clickService.getHistorySongByUserId(userId)
                )
        );
    }
}

