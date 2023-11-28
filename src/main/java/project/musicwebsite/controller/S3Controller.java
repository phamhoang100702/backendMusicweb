package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(path = "/api/v1/s3")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class S3Controller {
    @Autowired
    S3Service s3Service;

    @Autowired
    private S3Bucket s3Bucket;

    @Autowired
    HandleFile handleFile;

    @PostMapping(value = "/sounds", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseObject> upSound(@RequestParam("file") MultipartFile file) {
        System.out.println("okss");

        String name = handleFile.storeSoundFile(file);
        String path = "sounds/" + name;
        try {
            s3Service.putObject(this.s3Bucket.getName(), path, file.getBytes());
            return ResponseEntity.ok().body(
                    new ResponseObject("Ok", "success", name)
            );
        } catch (Exception e) {
            throw new FileUploadIoException("File is not acceptable");
        }
    }

    @GetMapping("/sounds/{fileName}")
    ResponseEntity<byte[]> getSound(@PathVariable String fileName) {
        try {
            String path = "sounds/"+fileName;

            byte[] bytes = s3Service.getObject(this.s3Bucket.getName(), path);
            return ResponseEntity.ok().body(
                    bytes
            );
        } catch (Exception e) {
            throw new NotFoundException("THIS FILE NOT EXISTED");
        }
    }

    @PostMapping("/lyrics")
    ResponseEntity<ResponseObject> upLyric(@RequestParam("file") MultipartFile file) {
        String name = handleFile.storeLyricsFile(file);
        String path = "lyrics/" + name;
        try {
            s3Service.putObject(this.s3Bucket.getName(), path, file.getBytes());
            return ResponseEntity.ok().body(
                    new ResponseObject("Ok", "success", path)
            );
        } catch (Exception e) {
            throw new FileUploadIoException("File is not acceptable");
        }
    }

    @GetMapping("/lyrics/{fileName}")
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


}
