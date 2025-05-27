package com.example.demo.repository;

import com.example.demo.model.Vibe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VibeRepository extends JpaRepository<Vibe, Long> {
    Vibe findVibeByCreatedAtAndUserId(LocalDate createdAt, long userId);

    List<Vibe> findAllByCreatedAtBetweenAndUserId(LocalDate createdAtAfter, LocalDate createdAtBefore, long userId);

    Vibe findVibeByIdAndUserId(long id, long userId);

    void deleteVibeByIdAndUserId(long id, long userId);
}
