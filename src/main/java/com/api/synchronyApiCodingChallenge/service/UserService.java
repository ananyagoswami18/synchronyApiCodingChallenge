package com.api.synchronyApiCodingChallenge.service;

import com.api.synchronyApiCodingChallenge.dto.UserRegistrationRequest;
import com.api.synchronyApiCodingChallenge.model.User;
import com.api.synchronyApiCodingChallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // --- Constructor ---

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encrypted password
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }
}
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public User registerUser(UserRegistrationRequest request) {
//        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already exists!");
//        }
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword())); // Store encoded password
//        user.setEmail(request.getEmail());
//
//        return userRepository.save(user);
//    }
//}
