package com.api.synchronyApiCodingChallenge.service;

import com.api.synchronyApiCodingChallenge.dto.UserRegistrationRequest;
import com.api.synchronyApiCodingChallenge.model.User;
import com.api.synchronyApiCodingChallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addImageToUser(String username, String imageUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        List<String> images = user.getImageUrls();
        images.add(imageUrl);
        user.setImageUrls(images);

        userRepository.save(user);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
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
