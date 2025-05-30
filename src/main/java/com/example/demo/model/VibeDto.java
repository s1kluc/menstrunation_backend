package com.example.demo.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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
    private String createdAt;
}
