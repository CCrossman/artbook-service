package com.artbook.service.controller;

import com.artbook.service.domain.ImageDTO;
import com.artbook.service.domain.ImageSearchRequest;
import com.artbook.service.domain.ImageUploadRequest;
import com.artbook.service.domain.Page;
import com.artbook.service.util.DAOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {
    private static final Logger logger = LoggerFactory.getLogger(ImagesController.class);

    @Autowired
    private DAOHelper daoHelper;

    @GetMapping
    public Page<ImageDTO> getImages(@RequestBody ImageSearchRequest request) {
        logger.info("getImages: {}", request);
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long imageId) {
        logger.info("getImage: {}", imageId);
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public ResponseEntity<Long> uploadImage(@RequestBody ImageUploadRequest request) {
        logger.info("uploadImage: {}", request);
        // returns imageId
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{imageId}/like")
    public ResponseEntity<Boolean> likeImage(@PathVariable Long imageId) {
        logger.info("likeImage: {}", imageId);
        // return true if increased like count
        throw new UnsupportedOperationException();
    }
}
