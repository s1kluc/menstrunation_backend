package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
public class Vibe {
    @Id
    private int id;
    private int userId;
    private LocalDateTime date;
    private long anger;
    private boolean period;
    private Integer blood; // Kann null sein, wenn keine Periode
    private String mood;

}
