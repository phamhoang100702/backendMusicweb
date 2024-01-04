//package project.musicwebsite.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import project.musicwebsite.entity.ResponseObject;
//import project.musicwebsite.entity.UPremium;
//import project.musicwebsite.entity.User;
//import project.musicwebsite.service.implement.PUserService;
//
//@RestController
//@RequestMapping(path = "/api/v1/premium")
//public class PreUserController {
//    @Autowired
//    PUserService pUserService;
//
//    @PutMapping("/{id}")
//    ResponseEntity<ResponseObject> update(
//            @PathVariable Long id,
//            @RequestBody UPremium uPremium
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "SAVE SUCCESS",
//                        pUserService.update(id, uPremium)
//                )
//        );
//    }
//
//    @GetMapping("/{id}")
//    ResponseEntity<ResponseObject> findById(
//            @PathVariable Long id
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "FINDING SUCCESS",
//                        pUserService.findById(id)
//                )
//        );
//    }
//
//    @GetMapping("")
//    ResponseEntity<ResponseObject> getAll() {
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "FINDING SUCCESS",
//                        pUserService.getAll()
//                )
//        );
//    }
//
//    @DeleteMapping("/{id}")
//    ResponseEntity<ResponseObject> delete(
//            @PathVariable Long id
//    ) {
//        pUserService.delete(id);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "DELETE SUCCESS",
//                        "{}"
//                )
//        );
//    }
//
//    @PostMapping("/{id}/user")
//    ResponseEntity<ResponseObject> switchToUser(
//            @PathVariable Long id
//    ) {
//        User user = pUserService.switchToNormalUser(id);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "SWITCH SUCCESS",
//                        user
//                )
//        );
//    }
//    @PostMapping("/{id1}/package/{id2}")
//    ResponseEntity<ResponseObject> addPackageToRegister(
//            @PathVariable Long id1,@PathVariable Long id2
//    ) {
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "SWITCH SUCCESS",
//                        pUserService.register(id1,id2)
//                )
//        );
//    }
//    @DeleteMapping("/{id1}/package/{id2}")
//    ResponseEntity<ResponseObject> removePackageToRegister(
//            @PathVariable Long id1,@PathVariable Long id2
//    ) {
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(
//                        "OK",
//                        "SWITCH SUCCESS",
//                        pUserService.removePackageFromRegister(id1,id2)
//                )
//        );
//    }
//
//
//}
