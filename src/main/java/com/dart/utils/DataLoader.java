package com.dart.utils;

import com.dart.dao.RoleRepository;
import com.dart.model.Role;
import com.dart.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles are already populated
        if (roleRepository.count() == 0) {
            // Populate roles if the table is empty
            for (UserRole role : UserRole.values()) {
                System.out.println("Loading roles into the table" + role);
                roleRepository.save(new Role(role.getRoleName()));
            }
        }
    }
}