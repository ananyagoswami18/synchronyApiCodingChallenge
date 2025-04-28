package com.api.synchronyApiCodingChallenge.service;

import com.api.synchronyApiCodingChallenge.dto.UserRegistrationRequest;
import com.api.synchronyApiCodingChallenge.model.User;
import com.api.synchronyApiCodingChallenge.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUserSuccessfully() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("john123");
        request.setPassword("password");
        request.setEmail("john@example.com");

        when(userRepository.findByUsername("john123")).thenReturn(Optional.empty());

        User savedUser = userService.registerUser(request);

        assertEquals("john123", savedUser.getUsername());
        assertTrue(passwordEncoder.matches("password", savedUser.getPassword()));
        assertEquals("john@example.com", savedUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserDuplicateUsername() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("john123");
        request.setPassword("password");
        request.setEmail("john@example.com");

        when(userRepository.findByUsername("john123")).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("Username already exists!", exception.getMessage());
    }
}
