package com.healthy_plate.post.infrastructure;

import com.healthy_plate.post.domain.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostImageRepository extends JpaRepository<PostImage, Long>, JpaPostRepository {
    
}
