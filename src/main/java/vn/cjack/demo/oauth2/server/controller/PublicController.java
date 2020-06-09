package vn.cjack.demo.oauth2.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cjack.demo.oauth2.server.model.request.RegisterReq;
import vn.cjack.demo.oauth2.server.service.UserService;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity hello(){
        return ResponseEntity.ok("Hello!");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterReq req){
        log.info("(register)");
        userService.register(req);
        return ResponseEntity.ok().build();
    }
}
