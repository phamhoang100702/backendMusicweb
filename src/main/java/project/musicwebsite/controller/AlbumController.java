package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.service.implement.AlbumService;

@RestController
@RequestMapping(path = "/api/v1/")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping("/album")
    ResponseEntity<ResponseObject> getAll( ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        albumService.getAll()
                )
        );
    }

    @GetMapping("/album/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        albumService.findById(id)
                )
        );
    }

    @PostMapping("/album")
    ResponseEntity<ResponseObject> save(@RequestBody Album album){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        albumService.save(album)
                )
        );
    }
    @PostMapping(path = "/album/{albumId}/song/{songId}")
    ResponseEntity<ResponseObject> addSongToAlbum(@PathVariable Long albumId,
                                                  @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Success", albumService.addSongToAlbum(albumId, songId))
        );
    }

    @DeleteMapping(path = "/album/{albumId}/song/{songId}")
    ResponseEntity<ResponseObject> removeSongFromAlbum(@PathVariable Long albumId,
                                                       @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Success",
                        albumService.removeSongFromAlbum(albumId, songId))
        );
    }
    @PutMapping("/album")
    ResponseEntity<ResponseObject> updateAlbum(@RequestBody Album album ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        albumService.update(album)
                )
        );
    }

    @DeleteMapping("/album/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        albumService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        "{}"
                )
        );
    }

    @GetMapping("/singer/{id}/album")
    ResponseEntity<ResponseObject> getAllAlbumBySinger(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        albumService.getBySinger(id)
                )
        );
    }

//    @GetMapping("/album/name={name}")
//    ResponseEntity<ResponseObject> getAlbumBySinger(@PathVariable String name){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "FINDING SUCCESS",
//                        albumService.getByName(name)
//                )
//        );
//    }
    @GetMapping("/album/{id}/song")
    ResponseEntity<ResponseObject> getAllSongByAlbum(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        albumService.getSongByAlbumId(id)
                )
        );
    }

//    @DeleteMapping("/album/{albumId}/song/{songId}")
//    ResponseEntity<ResponseObject> getAllSongByAlbum(@PathVariable Long albumId, @PathVariable Long songId) {
//        albumService.removeSongFromAlbum(albumId, songId);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "FINDING SUCCESS",
//                        albumService.getSongByAlbumId(albumId)
//                )
//        );
//    }





}
