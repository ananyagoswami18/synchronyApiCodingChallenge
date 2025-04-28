package com.api.synchronyApiCodingChallenge.controller;

import com.api.synchronyApiCodingChallenge.dto.UserRegistrationRequest;
import com.api.synchronyApiCodingChallenge.model.User;
import com.api.synchronyApiCodingChallenge.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
// @RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        User savedUser = userService.registerUser(request);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserProfile(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

}
