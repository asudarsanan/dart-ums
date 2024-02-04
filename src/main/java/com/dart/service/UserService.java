package com.dart.service;

import com.dart.dao.UserRepository;
import com.dart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    //add new user
    @Transactional
    public User createUser(User user){
        return userRepository.save(user);

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
            // Use matches method to compare raw password with hashed password
            return passwordEncoder.matches(password, user.getPassword()); // Passwords match, user is authenticated
        }
        return false; // Either user not found or passwords don't match
    }


    // get password for user
    // delete user
    // update email
    // update password


}
