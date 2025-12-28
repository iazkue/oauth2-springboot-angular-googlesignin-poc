package com.aghairsalon.authorizationserver.service;

import com.aghairsalon.authorizationserver.dto.CreateAppUserDTO;
import com.aghairsalon.authorizationserver.dto.MessageDTO;
import com.aghairsalon.authorizationserver.entity.AppUser;
import com.aghairsalon.authorizationserver.entity.Role;
import com.aghairsalon.authorizationserver.enums.RoleName;
import com.aghairsalon.authorizationserver.repository.AppUserRepository;
import com.aghairsalon.authorizationserver.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    void createUser_Success() {
        CreateAppUserDTO dto = new CreateAppUserDTO("testuser", "password", List.of("ROLE_USER"));
        Role role = new Role();
        role.setRole(RoleName.ROLE_USER);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleRepository.findByRole(RoleName.ROLE_USER)).thenReturn(Optional.of(role));
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(i -> i.getArguments()[0]);

        MessageDTO result = appUserService.createUser(dto);

        assertEquals("Usuario testuser guardado", result.message());
        verify(appUserRepository).save(any(AppUser.class));
    }

    @Test
    void createUser_RoleNotFound_ThrowsException() {
        CreateAppUserDTO dto = new CreateAppUserDTO("testuser", "password", List.of("ROLE_USER"));

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleRepository.findByRole(RoleName.ROLE_USER)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> appUserService.createUser(dto));
        verify(appUserRepository, never()).save(any());
    }
}
