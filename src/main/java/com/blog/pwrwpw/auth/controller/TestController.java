package com.blog.pwrwpw.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("테스트 성공");
    }
}
