package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.service.implement.CensorService;

@RestController
@RequestMapping(path = "/api/v1/censor")
public class CensorController {
    @Autowired
    CensorService censorService;



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("")
    ResponseEntity<ResponseObject> save(@RequestBody Censor censor) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        censorService.save(censor)
                )
        );
    }

    @GetMapping("/count")
    ResponseEntity<ResponseObject> countCensor(
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        censorService.getTotalCensor()
                )
        );
    }
    @GetMapping("")
    ResponseEntity<ResponseObject> searchAllByName(
            @RequestParam(name = "name", defaultValue = "") String name
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        censorService.searchAllByName(name)
                )
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        censorService.findById(id)
                )
        );
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> update(@RequestBody Censor censor) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        censorService.update( censor)
                )
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        censorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        "{}"
                )
        );
    }

}
