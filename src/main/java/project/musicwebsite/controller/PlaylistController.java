package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.model.request.AddingSongsToPlaylistRequest;
import project.musicwebsite.service.implement.PlaylistService;

@RestController
@RequestMapping(path = "/api/v1")
public class PlaylistController {
    @Autowired
    PlaylistService playlistService;

    @GetMapping("/playlist")
    ResponseEntity<ResponseObject> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FIND SUCCESS",
                        playlistService.getAll()
                )
        );
    }

    @GetMapping("user/{id}/playlist")
    ResponseEntity<ResponseObject> getAllPlaylistByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FIND SUCCESS",
                        playlistService.getAllPlaylistByUserId(id)
                )
        );
    }

    @GetMapping("user/{id}/playlist/favorite")
    ResponseEntity<ResponseObject> getFavoritePlaylist(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FIND SUCCESS",
                        playlistService.findFavoritePlaylist(id)
                )
        );
    }

    @GetMapping("/playlist/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FIND SUCCESS",
                        playlistService.findById(id)
                )
        );
    }

    @PostMapping("/user/{id}/playlist")
    ResponseEntity<ResponseObject> save(@PathVariable Long id, @RequestBody Playlist playlist) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "SAVE SUCCESS",
                        playlistService.save(id, playlist)
                )
        );
    }

    @PostMapping("/playlist")
    ResponseEntity<ResponseObject> save(@RequestBody Playlist playlist) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "SAVE SUCCESS",
                        playlistService.save(playlist)
                )
        );
    }

    @PutMapping("/playlist")
    ResponseEntity<ResponseObject> updatePlaylist(@RequestBody Playlist playlist) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "UPDATE SUCCESS",
                        playlistService.update(playlist)
                )
        );
    }

    @DeleteMapping("/playlist/{id}")
    ResponseEntity<ResponseObject> deletePlaylist(@PathVariable Long id) {
        playlistService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "DELETE SUCCESS",
                        "{}"
                )
        );
    }

    @PostMapping("/playlist/{id}/song")
    ResponseEntity<ResponseObject> addListSongToPlaylist(
            @RequestBody AddingSongsToPlaylistRequest arraySong,
            @PathVariable Long id
    ) {
        for (Long id1 : arraySong.getArraySong()) {
            System.out.println("sss" + id1);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.saveSongsToPlaylist(id, arraySong.getArraySong())

                )
        );
    }

    @GetMapping("/playlist/{id}/song")
    ResponseEntity<ResponseObject> getAllSongByPlaylistId(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.getAllSongByPlaylist(id))
        );

    }

    @GetMapping("/user/playlist")
    ResponseEntity<ResponseObject> searchAllPlaylistByNameForUser(
            @RequestParam(name = "name", defaultValue = "") String name
    ) {
        System.out.println("name" + name);
        return ResponseEntity.ok().body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.searchAllPlaylistByNameForUser(name))
        );
    }

    @GetMapping("/playlist/mainpage")
    ResponseEntity<ResponseObject> searchAllPlaylistForUserByName(
            @RequestParam(name = "name", defaultValue = "") String name
    ) {
        return ResponseEntity.ok().body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.searchAllMainPlaylist(name))
        );
    }

    @GetMapping("/playlist/count")
    ResponseEntity<ResponseObject> getTotalPlaylist(
    ) {
        return ResponseEntity.ok().body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.getTotalPlayList())
        );
    }

    @PostMapping("/user/{userId}/playlist/favorite/{songId}")
    ResponseEntity<ResponseObject> addSongToFavoritePlaylist(
            @PathVariable Long userId,
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok().body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.addSongToFavoritePlaylist(userId, songId))
        );
    }

    @DeleteMapping("/user/{userId}/playlist/favorite/{songId}")
    ResponseEntity<ResponseObject> removeSongFromFavoritePlaylist(
            @PathVariable Long userId,
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok().body(
                new ResponseObject(
                        "ok",
                        "SUCCESS",
                        playlistService.removeSongFromFavoritePlaylist(userId, songId))
        );
    }


    @PostMapping(path = "/playlist/{playlistId}/song/{songId}")
    ResponseEntity<ResponseObject> addSongToPlaylist(@PathVariable Long playlistId,
                                                     @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        playlistService.saveSongToPlaylist(songId, playlistId))
        );

    }

    @DeleteMapping(path = "/playlist/{playlistId}/song/{songId}")
    ResponseEntity<ResponseObject> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok",
                        "SUCCESS",
                        playlistService.removeSongFromPlaylist(songId, playlistId))
        );

    }

}
