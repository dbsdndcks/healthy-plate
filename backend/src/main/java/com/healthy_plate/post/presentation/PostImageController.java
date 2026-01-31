package com.healthy_plate.post.presentation;

import com.healthy_plate.post.application.PostImageService;
import com.healthy_plate.shared.s3.PresignedUrlRequest;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import com.healthy_plate.shared.s3.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostImageController {

    private final S3FileUploadService s3FileUploadService;
    private final PostImageService postImageService;

    @PostMapping("/image")
    public ResponseEntity<PresignedUrlResponse> uploadPostImage(
        final PresignedUrlRequest request
    ) {
        
    }

}
