package com.api.synchronyApiCodingChallenge.controller;

import com.api.synchronyApiCodingChallenge.service.ImgurService;
import com.api.synchronyApiCodingChallenge.service.UserService;
import com.api.synchronyApiCodingChallenge.kafka.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageControllerTest {

    private ImgurService imgurService;
    private UserService userService;
    private KafkaProducerService kafkaProducerService;
    private ImageController imageController;

    @BeforeEach
    void setup() {
        imgurService = mock(ImgurService.class);
        userService = mock(UserService.class);
        kafkaProducerService = mock(KafkaProducerService.class);

        imageController = new ImageController(imgurService, userService, kafkaProducerService);
    }

    @Test
    void testUploadImageSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());

        when(imgurService.uploadImage(file)).thenReturn("https://imgur.com/fakeImageUrl");

        ResponseEntity<String> response = imageController.uploadImage(file, "john123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("https://imgur.com/fakeImageUrl", response.getBody());

        verify(userService, times(1)).addImageToUser("john123", "https://imgur.com/fakeImageUrl");
        verify(kafkaProducerService, times(1)).sendMessage(anyString());
    }
}
