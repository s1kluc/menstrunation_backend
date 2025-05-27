package com.example.demo.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VibeDto {
    private Long id;
    private Long userId;
    private Long anger;
    private boolean period;
    private int blood;
    private String[] mood;
    private LocalDate createdAt;
}
