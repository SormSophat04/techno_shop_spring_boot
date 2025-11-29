package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public ResponseEntity<?> register(){
        return ResponseEntity.ok().build();
    }
}
