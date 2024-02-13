package com.dart.service;

import com.dart.dao.RoleRepository;
import com.dart.dao.UserRepository;
import com.dart.model.Role;
import com.dart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;


    }

    //add new user
    @Transactional
    public User createUser(User user){
        try {
            // Retrieve the role based on the role name
            Role role = roleRepository.findByRoleName(user.getRole().getRoleName());
            if (role != null) {
                user.setRole(role);
                userRepository.save(user);
            } else {
                // If the role does not exist, handle the error accordingly
                throw new RuntimeException("Role not found: " + user.getRole());
            }

            userRepository.save(user);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle duplicate entry error
            // For example, you can log the error or throw a custom exception
            // You can also return null or some indicator to the caller indicating failure
            // In this example, let's throw a custom exception
            throw new DuplicateKeyException("User with the same details already exists.");
        }

    }
    //get the user
    @Transactional(readOnly = true)
    public User getUser( String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUserEmail(Long userId, String newEmail) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(newEmail);
            return userRepository.save(existingUser);
        }
        return null; // User not found
    }

    @Transactional
    public User updateUserPassword(Long userId, String newPassword) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setPassword(newPassword);
            return userRepository.save(existingUser);
        }
        return null; // User not found
    }

    @Transactional(readOnly = true)
    public String getPasswordForUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElse(null);
        return existingUser != null ? existingUser.getPassword() : null;
    }

    @Transactional
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(password, user.getPassword()); // Passwords match, user is authenticated
        }
        return false; // Either user not found or passwords don't match
    }



    // get password for user
    // delete user
    // update email
    // update password


}
