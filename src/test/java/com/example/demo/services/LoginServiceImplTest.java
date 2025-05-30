package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private LoginServiceImpl loginService;

    @BeforeEach
    void setUp() {

    }
    @Test
    void login_validUser_returnsJwtToken() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("ILoveBockwurst69");

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(jwtUtils.createToken(mockUser)).thenReturn("bockwurst-token");

        String token = loginService.login(mockUser);

        assertNotNull(token);
        assertEquals("bockwurst-token", token);
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void login_invalidPassword_returnsNull() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("ILoveCockwurst96");

        User foundUser = new User();
        foundUser.setUsername("testuser");
        foundUser.setPassword("ILoveClubMate69");

        when(userRepository.findByUsername("testuser")).thenReturn(foundUser);

        String token = loginService.login(mockUser);

        assertNull(token);
    }

    @Test
    void login_userNotFound_returnsNull() {
        when(userRepository.findByUsername("notfound"))
            .thenReturn(null);

        String token = loginService.login(new User());

        assertNull(token);
    }
}
