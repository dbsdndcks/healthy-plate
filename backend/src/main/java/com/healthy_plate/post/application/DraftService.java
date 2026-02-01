package com.healthy_plate.post.application;

import com.healthy_plate.post.domain.model.Draft;
import com.healthy_plate.post.domain.model.ImageStatus;
import com.healthy_plate.post.domain.model.PostImage;
import com.healthy_plate.post.domain.repository.DraftRepository;
import com.healthy_plate.shared.error.exception.BusinessErrorCode;
import com.healthy_plate.shared.error.exception.BusinessException;
import com.healthy_plate.shared.s3.AllowedImageType;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import com.healthy_plate.shared.s3.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DraftService {

    private final DraftRepository draftRepository;
    private final S3FileUploadService s3FileUploadService;
    
    public Long create(final Long writerId) {
        final Draft draft = Draft.createByWriter(writerId);
        final Draft savedDraft = draftRepository.save(draft);
        return savedDraft.getId();
    }

    @Transactional
    public PresignedUrlResponse saveImageToDraft(
        final Long userId,
        final Long resourceId,
        final AllowedImageType imageType,
        final Long fileSize
    ) {
        Draft draft = draftRepository.findById(resourceId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.DRAFT_NOT_FOUND));

        PresignedUrlResponse response = s3FileUploadService.getPostImagePreSignedUrl(
            String.valueOf(userId),
            String.valueOf(resourceId),
            imageType,
            fileSize
        );

        PostImage postImage = PostImage.create(response.fileUrl(), ImageStatus.TEMP);
        draft.addImage(postImage);

        draftRepository.save(draft);

        return response;
    }
}
