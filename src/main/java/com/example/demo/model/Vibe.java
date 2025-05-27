package com.example.demo.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;



@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Builder
public class Vibe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long anger;
    private boolean period;
    private int blood;
    private String mood;
    private LocalDate createdAt;
}