package com.healthy_plate.post.domain.repository;

import com.healthy_plate.post.domain.model.Draft;
import java.util.Optional;

public interface DraftRepository {

    Draft save(Draft draft);

    Optional<Draft> findById(Long draftId);
}
