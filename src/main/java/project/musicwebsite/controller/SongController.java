package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Song;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.service.implement.SongService;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class SongController {
    @Autowired
    SongService songService;

    @PostMapping(path = "/singer/{singerId}/song")
    ResponseEntity<ResponseObject> save(@PathVariable Long singerId,@RequestBody Song song){
        Song song1 = songService.save(singerId,song);
        return ResponseEntity.ok().body(
                new ResponseObject("ok","SUCCESS",song)
        );
    }

    @GetMapping(path="/song/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Song> song = songService.findById(id);
        return ResponseEntity.ok().body(
                new ResponseObject("ok","SUCCESS",song)
        );
    }

    @GetMapping(path = "/song")
    ResponseEntity<ResponseObject> getAll(){
            return ResponseEntity.ok().body(
                    new ResponseObject("ok", "SUCCESS", songService.getAll())
            );

    }

    @GetMapping(path = "/song?name={name}")
    ResponseEntity<ResponseObject> searchByName(@PathVariable String name){
        return ResponseEntity.ok().body(
                new ResponseObject("ok","SUCCESS",songService.searchByName(name))
        );
    }

    @PutMapping(path = "/song/{id}")
    ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody Song song){
        return ResponseEntity.ok().body(
                new ResponseObject("ok","SUCCESS",songService.update(id,song))
        );
    }

    @DeleteMapping(path="/song/{id}")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id){
        songService.delete(id);
        return ResponseEntity.ok().body(
                new ResponseObject("ok","Success","{}")
        );
    }



}
