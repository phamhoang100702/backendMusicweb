package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.service.implement.CensorService;

@RestController
@RequestMapping(path = "/api/v1/censor")
public class CensorController {
    @Autowired
    CensorService censorService;

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@RequestBody Censor censor) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        censorService.save(censor)
                )
        );
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        censorService.getAll()
                )
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        censorService.findById(id)
                )
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@PathVariable Long id,
                                          @RequestBody Censor censor) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        censorService.update(id, censor)
                )
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        censorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "OK",
                        "FINDING SUCCESS",
                        "{}"
                )
        );
    }

}
