package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Category;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.Song;
import project.musicwebsite.service.implement.SongService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class SongController {
    @Autowired
    SongService songService;

    @PostMapping(path = "/creator/{singerId}/song")
    ResponseEntity<ResponseObject> save(@PathVariable Long singerId, @RequestBody Song song) {
        Song song1 = songService.save(singerId, song);
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "SUCCESS", song)
        );
    }

    @GetMapping(path = "/song/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Song> song = songService.findById(id);
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "SUCCESS", song)
        );
    }

    @PutMapping(path = "/song")
    ResponseEntity<ResponseObject> update( @RequestBody Song song) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "SUCCESS", songService.update( song))
        );
    }

    @DeleteMapping(path = "/song/{id}")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        songService.delete(id);
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Success", "{}")
        );
    }

    @PostMapping(path = "/album/{albumId}/song/{songId}")
    ResponseEntity<ResponseObject> addSongToAlbum(@PathVariable Long albumId,
                                                  @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Success", songService.addSongToAlbum(albumId, songId))
        );
    }

    @DeleteMapping(path = "/album/{albumId}/song/{songId}")
    ResponseEntity<ResponseObject> removeSongFromAlbum(@PathVariable Long albumId,
                                                  @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Success",
                        songService.removeSongFromAlbum(albumId, songId))
        );
    }

    @GetMapping(path = "/creator/{id}/song")
    ResponseEntity<ResponseObject> getSongByCreatorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "SUCCESS", songService.findSongByCreatorId(id))
        );

    }

    @PostMapping(path = "/playlist/{playlistId}/song/{songId}")
    ResponseEntity<ResponseObject> addSongToPlaylist(@PathVariable Long playlistId,
                                                     @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.saveSongToPlaylist(songId, playlistId))
        );

    }

    @DeleteMapping(path = "/playlist/{playlistId}/song/{songId}")
    ResponseEntity<ResponseObject> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.removeSongFromPlaylist(songId, playlistId))
        );

    }

    @PostMapping(path = "/song/patch")
    ResponseEntity<ResponseObject> saveAllSong(@RequestBody List<Song> songs) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.saveListSong(songs))
        );

    }

    @PostMapping(path = "/song")
    ResponseEntity<ResponseObject> save(@RequestBody Song song) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.save(song))
        );

    }

    @PostMapping("/song/{id}/singer")
    ResponseEntity<ResponseObject> saveSingerToSong(@PathVariable Long id,
                                                    @RequestBody List<Singer> singers) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.saveSingersToSong(id, singers)
                )
        );

    }

    @DeleteMapping("/song/{id}/singer")
    ResponseEntity<ResponseObject> removeSingerFromSong(@PathVariable Long id,
                                                        @RequestBody List<Singer> singers) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.removeSingerFromSong(id, singers)
                )
        );

    }

    @GetMapping(path = "/song/page")
    ResponseEntity<ResponseObject> searchByNamePage(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "SUCCESS",
                        songService.searchSongPage(name, (pageNo - 1) * pageSize, pageSize)
                )
        );
    }

    @GetMapping(path = "/song")
    ResponseEntity<ResponseObject> searchByName(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "status",required = false ) Integer status
    ) {
        List<Song> songs = new LinkedList<>();
        if(status == null){
            songs = songService.searchByName(name);
        }
        else {
            songs = songService.searchByName(name,status);
        }

        return ResponseEntity.ok().body(
                new ResponseObject("ok", "SUCCESS",
                        songs
                )
        );
    }


    @PostMapping("/song/{id}/category")
    ResponseEntity<ResponseObject> saveCategoryToSong(@PathVariable Long id,
                                                      @RequestBody List<Category> categories) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.addCategoryToSong(id, categories)
                )
        );

    }

//    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/song/{id}/category")
    ResponseEntity<ResponseObject> removeCategoryFromSong(@PathVariable Long id,
                                                          @RequestBody List<Category> categories) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.removeCategoryToSong(id, categories)
                )
        );
    }

    @GetMapping("/song/count")
    ResponseEntity<ResponseObject> getTotalSong() {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.getTotalSong()
                )
        );
    }

    @GetMapping("/category/{categoryId}/song")
    ResponseEntity<ResponseObject> getAllSongByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.getSongsByCategoryId(categoryId)
                )
        );
    }

    @GetMapping("/singer/{singerId}/song")
    ResponseEntity<ResponseObject> getAllSongBySingerId(@PathVariable Long singerId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        songService.getSongsBySingerId(singerId)
                )
        );
    }
}
