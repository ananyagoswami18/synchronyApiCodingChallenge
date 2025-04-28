package com.api.synchronyApiCodingChallenge.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImgurService {
    @Value("${imgur.client.id}")
    private String clientId;

    @Value("${imgur.upload.url}")
    private String uploadUrl;

    @Value("${imgur.delete.url}")
    private String deleteUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadImage(MultipartFile file) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Client-ID " + clientId);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", Base64.getEncoder().encodeToString(file.getBytes()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return (String) data.get("link"); // Return uploaded image URL
        } else {
            throw new Exception("Failed to upload image to Imgur");
        }
    }

    public void deleteImage(String deleteHash) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Client-ID " + clientId);
        System.out.println("Authorization: Client-ID " + clientId);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(deleteUrl.replace("{deleteHash}", deleteHash), HttpMethod.DELETE, requestEntity, Map.class);
    }
}
