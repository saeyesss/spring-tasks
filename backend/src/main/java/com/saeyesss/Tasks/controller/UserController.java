package com.saeyesss.Tasks.controller;

import com.saeyesss.Tasks.dto.Response;
import com.saeyesss.Tasks.dto.UserRequest;
import com.saeyesss.Tasks.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response<?>> signUp(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.signUp(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@Valid @RequestBody UserRequest userRequest) {
        {
            return ResponseEntity.ok(userService.login(userRequest));
        }
    }

}
