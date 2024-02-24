package com.dart.usermanagementservice;

import com.dart.dao.RoleRepository;
import com.dart.dao.UserRepository;
import com.dart.model.Role;
import com.dart.model.User;
import com.dart.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@SpringBootTest
@ActiveProfiles("test")
public class RoleServiceTest {

    @Mock
    private UserRepository userMockRepository;

    @Mock
    private RoleRepository roleMockRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void testUpdateUserRole_Success() {
        // Prepare mock data
        String email = "user@example.com";
        String roleName = "ADMIN";
        User existingUser = new User("username", email, "password");
        Role existingRole = new Role("USER");
        Role newRole = new Role(roleName);
        // Optionally set other fields as needed
        existingUser.setAddress("123 Main St");
        existingUser.setPhoneNumber("1234567890");
        existingUser.setCreatedAt(new Date()); // Set timestamps if required
//        userMockRepository.save(existingUser);


        existingUser.setRole(existingRole);

        // Mock repository behavior
        Mockito.when(userMockRepository.findByEmail(email)).thenReturn(existingUser);
        Mockito.when(roleMockRepository.findByRoleName(roleName)).thenReturn(newRole);

//        System.out.println((userMockRepository.findByEmail(email)).getEmail());

        // Test
        User updatedUser = roleService.updateUserRole(email, roleName);

        // Verify repository method calls
        verify(userMockRepository, times(1)).findByEmail(email);
        verify(roleMockRepository, times(1)).findByRoleName(roleName);
        verify(userMockRepository, times(1)).save(existingUser);

        // Assertions
        Assertions.assertEquals(roleName, existingUser.getRole().getRoleName());
    }
}