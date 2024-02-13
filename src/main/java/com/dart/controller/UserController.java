package com.dart.controller;

import com.dart.model.Login;
import com.dart.model.User;
import com.dart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<?> userRegistration(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (DataIntegrityViolationException e) {
            // Handle duplicate entry error
            // For example, you can return a custom response entity indicating failure
            String errorMessage = "User registration failed. A user with the same details already exists.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); // Or provide a custom error message
        }
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
    public ResponseEntity<String> authenticateUser(@RequestBody Login loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("User authenticated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


//    @PostMapping("/role")
//    public ResponseEntity<String> createRole(@RequestBody Role role) {
//        userService.updateUserRole(role);
//        return ResponseEntity.ok("Role saved successfully");
//    }



}
