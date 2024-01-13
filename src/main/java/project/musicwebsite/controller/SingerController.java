package project.musicwebsite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.service.implement.SingerService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/singer")
public class SingerController {

    @Autowired
    SingerService singerService;

    @PostMapping("/{id1}/user/{id2}")
    ResponseEntity<ResponseObject> follow(@PathVariable Long id1, @PathVariable Long id2) {
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", singerService.follow(id2, id1))
        );
    }


//    @GetMapping("")
//    ResponseEntity<ResponseObject> getAll() {
//        List<Singer> list = singerService.getAll();
//        return ResponseEntity.ok(
//                new ResponseObject("ok", "SUCCESS", list)
//        );
//    }

    @GetMapping("")
    ResponseEntity<ResponseObject> searchByNameOrNickName(
            @RequestParam(name = "name" , defaultValue = "")
            String name,
            @RequestParam(name = "status",required = false)
            Boolean status
    ) {
        List<Singer> singers = new LinkedList<>();
        System.out.println("status: " + status);
        if(status == null){
            singers = singerService.searchByNameOrNickName(name);
        }
        else {
            singers = singerService.searchByNameOrNickName(name,status);
        }
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS",
                       singers)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Singer> singer = singerService.findById(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", singer)
        );
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> update(
            @Valid @RequestBody Singer singer) {
        Singer BSinger1 = singerService.update(singer);
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", BSinger1)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        singerService.delete(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", "{}")
        );
    }

    @DeleteMapping("/{id1}/user/{id2}")
    ResponseEntity<ResponseObject> unFollow(@PathVariable Long id1, @PathVariable Long id2) {
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", singerService.unFollow(id2, id1))
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@Valid @RequestBody Singer singer) {
        Singer singer1 = singerService.save(singer);
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", singer1)
        );
    }

    @GetMapping("/{id}/follower")
    ResponseEntity<ResponseObject> findAllFollowers(@PathVariable Long id) {
        List<User> list = singerService.findAllFollower(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "ALL FOLLOWERS", list)
        );
    }

    @GetMapping("/{id}/follower/count")
    ResponseEntity<ResponseObject> countAllFollower(@PathVariable Long id) {
        List<User> list = singerService.findAllFollower(id);
        return ResponseEntity.ok(
                new ResponseObject("ok", "ALL FOLLOWERS", list)
        );
    }

    @PostMapping("/patch")
    ResponseEntity<ResponseObject> savePatchSinger(@RequestBody List<Singer> singers) {
        List<Singer> list = singerService.addPatch(singers);
        return ResponseEntity.ok(
                new ResponseObject("ok", "ALL FOLLOWERS", list)
        );
    }

    @GetMapping("/top/{top}")
    ResponseEntity<ResponseObject> getTopTenSingerByFollowers(
            @PathVariable Long top
    ) {
//        List<Singer> list = singerService.addPatch(singers);
        return ResponseEntity.ok(
                new ResponseObject("ok", "SUCCESS", singerService.getTopTenSingerByFollowers(top))
        );
    }

    @GetMapping("/count")
    ResponseEntity<ResponseObject> countSinger(
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        singerService.getTotalSinger()
                )
        );
    }
}

