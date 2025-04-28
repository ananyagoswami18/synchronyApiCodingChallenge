package com.api.synchronyApiCodingChallenge.controller;

import com.api.synchronyApiCodingChallenge.kafka.KafkaProducerService;
import com.api.synchronyApiCodingChallenge.service.ImgurService;
import com.api.synchronyApiCodingChallenge.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
//@RequiredArgsConstructor
public class ImageController {
    private final ImgurService imgurService;
    private final UserService userService;
    private final   KafkaProducerService kafkaProducerService;

    // --- Constructor (manual) ---
    public ImageController(ImgurService imgurService, UserService userService, KafkaProducerService kafkaProducerService) {
        this.imgurService = imgurService;
        this.userService = userService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        try {
            String imageUrl = imgurService.uploadImage(file);
            userService.addImageToUser(username, imageUrl);

            // Publish message to Kafka
            String message = "User: " + username + ", uploaded Image URL: " + imageUrl;
            kafkaProducerService.sendMessage(message);

            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{deleteHash}")
    public ResponseEntity<String> deleteImage(@PathVariable String deleteHash) {
        try {
            imgurService.deleteImage(deleteHash);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//    private final ImgurService imgurService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            String imageUrl = imgurService.uploadImage(file);
//            return ResponseEntity.ok(imageUrl);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/delete/{deleteHash}")
//    public ResponseEntity<String> deleteImage(@PathVariable String deleteHash) {
//        try {
//            imgurService.deleteImage(deleteHash);
//            return ResponseEntity.ok("Image deleted successfully");
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
