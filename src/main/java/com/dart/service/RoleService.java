package com.dart.service;

import com.dart.dao.RoleRepository;
import com.dart.dao.UserRepository;
import com.dart.model.Role;
import com.dart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public RoleService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }
    @Transactional
    public User updateUserRole(Long userId, String roleName) {
        try {
            User existingUser = userRepository.findById(userId).orElse(null);
            if (existingUser != null) {
                Role existingRole = existingUser.getRole();
                if (existingRole != null && existingRole.getRoleName().equals(roleName)) {
                    throw new RuntimeException("Role change not required: " + existingRole.getRoleName());
                }

                Role newRole = roleRepository.findByRoleName(roleName);
                if (newRole == null) {
                    throw new RuntimeException("Role not found: " + roleName);
                }

                existingUser.setRole(newRole);
                return userRepository.save(existingUser);
            } else {
                throw new RuntimeException("User not found: " + userId);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("User doesn't exist");
        }
    }

}
