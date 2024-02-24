package com.dart.usermanagementservice;

import com.dart.dao.RoleRepository;
import com.dart.dao.UserRepository;
import com.dart.model.Role;
import com.dart.model.User;
import com.dart.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void testUpdateUserRole_Success() {
        // Mock data
        User existingUser = new User();
        existingUser.setId(1L);
        Role existingRole = new Role();
        existingRole.setId(1L);
        existingRole.setRoleName("Role1");
        existingUser.setRole(existingRole);
        Role newRole = new Role();
        newRole.setId(2L);
        newRole.setRoleName("NewRole");


        // Mock repository behavior
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(roleRepository.findByRoleName("NewRole")).thenReturn(newRole);

        // Test
        User updatedUser = roleService.updateUserRole(1L, "NewRole");

        // Verify repository method calls
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);

        // Assertions
        Assertions.assertEquals("NewRole", existingUser.getRole().getRoleName());
    }

}
