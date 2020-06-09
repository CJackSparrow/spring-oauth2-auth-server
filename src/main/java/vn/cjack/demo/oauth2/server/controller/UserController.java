package vn.cjack.demo.oauth2.server.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cjack.demo.oauth2.server.model.request.ChangePasswordReq;
import vn.cjack.demo.oauth2.server.model.request.RegisterReq;
import vn.cjack.demo.oauth2.server.service.UserService;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/lock/{userId}")
    public ResponseEntity lockUser(@PathVariable("userId") Long userId) throws NotFoundException {
        userService.lockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-password")
    public ResponseEntity changePassWord(@RequestBody ChangePasswordReq req){
        userService.changePassword(req);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity resetPassWord(){
        log.info("(resetPassWord)");
        userService.resetPassword();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ahihi")
    public ResponseEntity ahihi(){
        log.info("(ahihi)");
        return ResponseEntity.ok().build();
    }

}
