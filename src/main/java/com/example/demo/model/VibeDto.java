package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VibeDto {
    private int id;
    private int userId;
    private LocalDateTime date;
    private long anger;
    private boolean period;
    private Integer blood;
    private String[] mood;
}
