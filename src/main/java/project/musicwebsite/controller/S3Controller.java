package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.exception.FileUploadIoException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.s3.HandleFile;
import project.musicwebsite.s3.S3Bucket;
import project.musicwebsite.s3.S3Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/s3")
//@CrossOrigin(origins = "http://127.0.0.1    :5500")
public class S3Controller {
    @Autowired
    S3Service s3Service;

    @Autowired
    private S3Bucket s3Bucket;

    @Autowired
    HandleFile handleFile;

    final static String path = "https://musicwebsite.s3.ap-east-1.amazonaws.com/";

    @PostMapping(value = "", consumes = MediaType.ALL_VALUE)
    ResponseEntity<ResponseObject> upFile(@RequestParam(name = "sound",required = false) MultipartFile sound,
                                          @RequestParam(name = "lyric", required = false) MultipartFile lyric,
                                          @RequestParam(name = "avatar", required = false) MultipartFile avatar) throws Exception   {
        System.out.println("ok");

            String name = handleFile.generatedName();
            String songFile, lyricFile, avatarFile;
            Map<String,String> urls = new HashMap<>();
            urls.put("sound","");
            urls.put("lyric","");
            urls.put("avatar","");

            if(sound != null && handleFile.isSoundFile(sound)){

                String soundExtension = handleFile.fileExtension(sound);
                songFile = "song/sounds/" + name + "." + soundExtension;
                s3Service.putObject(this.s3Bucket.getName(), songFile, sound.getBytes());
                String url1 = path + songFile;
                urls.put("sound",url1);
            }
            if ( lyric!=null && handleFile.isLyricFile(lyric)) {
                String lyricExtension = handleFile.fileExtension(lyric);
                lyricFile = "song/lyrics/" + name + "." + lyricExtension;
                s3Service.putObject(this.s3Bucket.getName(), lyricFile, lyric.getBytes());
                String url2 = path + lyricFile;
                urls.put("lyric",url2);
            }
            if(avatar!=null && handleFile.isImageFile(avatar)){
                String avatarExtension = handleFile.fileExtension(avatar);
                avatarFile = "song/avatars/" + name + "." + avatarExtension;
                String url3 = path + avatarFile;
                s3Service.putObject(this.s3Bucket.getName(), avatarFile, avatar.getBytes());
                urls.put("avatar",url3);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "success", urls)
            );

    }

    @PostMapping(value = "/sound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseObject> upSound(@RequestParam("file") MultipartFile file) {

        String name = handleFile.storeSoundFile(file);
        String pathSong = "song/sounds/" + name;
        String url = path + pathSong;
        try {
            s3Service.putObject(this.s3Bucket.getName(), pathSong, file.getBytes());

            return ResponseEntity.ok().body(
                    new ResponseObject("Ok", "success", url)
            );
        } catch (Exception e) {
            throw new FileUploadIoException("File is not acceptable");
        }
    }

    @DeleteMapping(value = "/{deleteFile}")
    ResponseEntity<ResponseObject> deleteFile(@PathVariable String deleteFile) {
        s3Service.delete(s3Bucket.getName(), deleteFile);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Delete Success", "Ok", "")
                );

    }

    @GetMapping("/sound/{fileName}")
    ResponseEntity<byte[]> getSound(@PathVariable String fileName) {
        try {
            String path = "sounds/" + fileName;

            byte[] bytes = s3Service.getObject(this.s3Bucket.getName(), path);
            return ResponseEntity.ok().body(
                    bytes
            );
        } catch (Exception e) {
            throw new NotFoundException("THIS FILE NOT EXISTED");
        }
    }

    @PostMapping("/lyric")
    ResponseEntity<ResponseObject> upLyric(@RequestParam("file") MultipartFile file) {
        String name = handleFile.storeLyricsFile(file);
        String pathLyrics = "song/lyrics/" + name;
        String url = path + pathLyrics;
        try {
            s3Service.putObject(this.s3Bucket.getName(), path, file.getBytes());
            return ResponseEntity.ok().body(
                    new ResponseObject("Ok", "success", url)
            );
        } catch (Exception e) {
            throw new FileUploadIoException("File is not acceptable");
        }
    }

    @GetMapping("/lyric/{fileName}")
    ResponseEntity<ResponseObject> getLyric(@PathVariable String fileName) {
        try {
            byte[] bytes = s3Service.getObject(this.s3Bucket.getName(), fileName);
            return ResponseEntity.ok().body(
                    new ResponseObject("ok", "GET FILE SUCCESS", bytes)
            );
        } catch (Exception e) {
            throw new NotFoundException("THIS FILE NOT EXISTED");
        }
    }

    @PostMapping("/avatar")
    ResponseEntity<ResponseObject> upImages(@RequestParam(value = "avatar",required = false) MultipartFile file) {
        String name = handleFile.storeFileImages(file);
        String pathImages = "Avatar/" + name;
        String url = path + pathImages;
        try {
            s3Service.putObject(this.s3Bucket.getName(), pathImages, file.getBytes());
            return ResponseEntity.ok().body(
                    new ResponseObject("Ok", "success", url)
            );
        } catch (Exception e) {
            throw new FileUploadIoException("File is not acceptable");
        }
    }


}
