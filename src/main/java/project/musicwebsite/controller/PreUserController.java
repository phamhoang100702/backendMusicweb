package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.UPremium;
import project.musicwebsite.service.implement.PUserService;

@RestController
@RequestMapping(path = "/api/v1/premium")
public class PreUserController {
    @Autowired
    PUserService pUserService;

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(
            @PathVariable Long id,
            @RequestBody UPremium uPremium
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        pUserService.update(id, uPremium)
                )
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        pUserService.findById(id)
                )
        );
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        pUserService.getAll()
                )
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(
            @PathVariable Long id
    ) {
        pUserService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        "{}"
                )
        );
    }


}
