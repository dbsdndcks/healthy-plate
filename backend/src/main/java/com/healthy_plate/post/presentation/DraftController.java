package com.healthy_plate.post.presentation;

import com.healthy_plate.post.application.DraftService;
import com.healthy_plate.shared.s3.AllowedImageType;
import com.healthy_plate.shared.s3.PresignedUrlRequest;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/draft")
@RequiredArgsConstructor
public class DraftController {

    private final DraftService draftService;

    @PostMapping
    public ResponseEntity<Long> create(@AuthenticationPrincipal final Long writerId) {
        final Long DraftId = draftService.create(writerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(DraftId);
    }

    @PostMapping("/image")
    public ResponseEntity<PresignedUrlResponse> uploadImage(
        @AuthenticationPrincipal final Long userId,
        final Long resourceId,
        final PresignedUrlRequest request
    ) {
        final PresignedUrlResponse presignedUrlResponse = draftService.saveImageToDraft(
            userId,
            resourceId,
            AllowedImageType.fromContentType(request.contentType()),
            request.fileSize()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(presignedUrlResponse);
    }
}
