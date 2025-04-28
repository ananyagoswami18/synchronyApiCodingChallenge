package com.api.synchronyApiCodingChallenge.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaCnsumerService {
    @KafkaListener(topics = "image-uploads", groupId = "image-upload-group")
    public void listen(String message) {
        System.out.println("Received Message in Kafka Consumer: " + message);
    }
}
