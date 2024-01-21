package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.Category;
import project.musicwebsite.entity.Censor;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.service.implement.CategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/patch")
    ResponseEntity<ResponseObject> saveAll(@RequestBody List<Category> list) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        categoryService.addList(list)
                )
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@RequestBody Category category) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        categoryService.save(category)
                )
        );
    }


    @GetMapping("")
    ResponseEntity<ResponseObject> findAll() {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        categoryService.getAll()
                )
        );
    }

    @GetMapping("{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        categoryService.findById(id)
                )
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        "FINDING SUCCESS",
                        ""
                )
        );
    }
}
