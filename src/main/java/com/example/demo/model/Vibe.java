package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Builder
public class Vibe {
    @Id
    private long id;
    private long userId;
    private long anger;
    private boolean period;
    private int blood;
    private String mood;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}