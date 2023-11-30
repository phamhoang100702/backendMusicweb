package project.musicwebsite.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.PremiumPackage;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.repositories.PackageRepository;
import project.musicwebsite.service.implement.PackageService;

@RestController
@RequestMapping(path = "/api/v1/package")
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@RequestBody PremiumPackage premiumPackage) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        packageService.save(premiumPackage))
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        packageService.findById(id))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updatePackage(
            @PathVariable Long id,
            @RequestBody PremiumPackage premiumPackage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        packageService.update(id, premiumPackage))
        );
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        packageService.getAll())
        );
    }

    @DeleteMapping("")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        packageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok",
                        "SAVE SUCCESS",
                        "{}")
        );
    }
}
