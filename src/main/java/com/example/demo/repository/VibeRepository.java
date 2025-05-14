package com.example.demo.repository;

import com.example.demo.model.Vibe;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VibeRepository extends CrudRepository<Vibe, Long> {
    Vibe findVibeByIdEqualsAndUserIdEquals(long id, long userId);

    List<Vibe> findAllByCreatedAtBetweenAndUserId(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore, long userId);

    Vibe findVibeByCreatedAtEqualsAndUserId(LocalDateTime createdAt, long userId);
}
