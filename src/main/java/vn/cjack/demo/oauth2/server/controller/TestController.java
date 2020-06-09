package vn.cjack.demo.oauth2.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity getPublic(){
        return ResponseEntity.ok("AHIHI");

    }

    @GetMapping("/private")
    public ResponseEntity getPrivate(){
        return ResponseEntity.ok("AHIHI");

    }
}
