package com.artbook.service.controller;

import com.artbook.service.domain.ImageDTO;
import com.artbook.service.domain.Page;
import com.artbook.service.util.DAOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {
    private static final Logger logger = LoggerFactory.getLogger(ImagesController.class);

    @Autowired
    private DAOHelper daoHelper;

    @GetMapping
    public Page<ImageDTO> getImages(@RequestParam MultiValueMap<String, String> queryParams) {
        logger.info("getImages: {}", queryParams);
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long imageId) {
        logger.info("getImage: {}", imageId);
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public ResponseEntity<Long> uploadImage(@RequestBody Object request) {
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
