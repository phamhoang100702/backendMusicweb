package project.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.musicwebsite.entity.ResponseObject;
import project.musicwebsite.entity.Role;
import project.musicwebsite.service.implement.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> save(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Save success", "ok",
                                roleService.save(role))
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Save success", "ok",
                                roleService.findById(id))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        roleService.deleteRole(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject("Save success", "ok",
                                "")
                );
    }
}
