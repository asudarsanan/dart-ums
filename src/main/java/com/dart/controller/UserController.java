package com.dart.controller;

import com.dart.model.User;
import com.dart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> userRegistration(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody User loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("User authenticated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }



}
