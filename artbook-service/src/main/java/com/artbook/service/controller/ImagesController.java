package com.artbook.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {
    private static final Logger logger = LoggerFactory.getLogger(ImagesController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${DAO_HOST}")
    private String host;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        logger.info("ImagesController connecting RestClient to {}", host);
        this.restClient = RestClient.create(host);
    }

    @GetMapping
    public ResponseEntity<Page<JsonNode>> getImageMetadata(@RequestParam MultiValueMap<String, String> queryParams) {
        logger.info("getImageMetadata: {}", queryParams);
        try {
            // Execute the Request using RestClient
            ResponseEntity<Page<String>> response = restClient.get()
                .uri("/api/v1/images")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Page<String>>() {});

            Page<JsonNode> body = response.getBody().map(this::readTree);
            return ResponseEntity.status(response.getStatusCode()).body(body);
        } catch (Exception ex) {
            logger.error("Error reading images", ex);
            return ResponseEntity.internalServerError()
                .header("x-error-type", ex.getClass().getName())
                .header("x-error-message", ex.getMessage())
                .build();
        }
    }

    @GetMapping("/{imageId}/{imageType}")
    public ResponseEntity<JsonNode> getImageMetadata(@PathVariable long imageId, @PathVariable String imageType) {
        logger.info("getImageMetadata: {}, {}", imageId, imageType);
        try {
            // Execute the Request using RestClient
            ResponseEntity<String> response = restClient.get()
                .uri("/api/v1/images/" + imageId + "/" + imageType)
                .retrieve()
                .toEntity(String.class);

            JsonNode jsonBody = objectMapper.readTree(response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body(jsonBody);
        } catch (Exception ex) {
            logger.error("Error reading image file", ex);
            return ResponseEntity.internalServerError()
                .header("x-image-id", String.valueOf(imageId))
                .header("x-image-type", imageType)
                .header("x-error-type", ex.getClass().getName())
                .header("x-error-message", ex.getMessage())
                .build();
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<JsonNode> uploadImage(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "tags", required = false) List<String> tags
    ) {
        try {
            // Build the Multipart Body
            MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();

            // CRITICAL: You must call .getResource() on the MultipartFile
            multipartBody.add("file", image.getResource());

            // Add your other standard parameters
            multipartBody.add("title", title);
            multipartBody.add("description", description);
            multipartBody.addAll("tags", tags);

            // Execute the Request using RestClient
            ResponseEntity<String> response = restClient.post()
                .uri("/api/v1/images")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipartBody)
                .retrieve()
                .toEntity(String.class);

            JsonNode jsonBody = objectMapper.readTree(response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body(jsonBody);
        } catch (Exception ex) {
            logger.error("Problem uploading image.", ex);
            return ResponseEntity.internalServerError()
                .header("x-error-type", ex.getClass().getName())
                .header("x-error-message", ex.getMessage())
                .build();
        }
    }

    private JsonNode readTree(String rawJson) {
        try {
            if (rawJson == null) {
                return NullNode.getInstance();
            }
            return objectMapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
