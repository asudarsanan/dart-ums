package com.dart.controller;

import com.dart.model.User;
import com.dart.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PutMapping("/{email}")
    public ResponseEntity<?> updateUserRole(@PathVariable String email, @RequestParam String roleName) {
        try {
            User updatedUser = roleService.updateUserRole(email, roleName);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
