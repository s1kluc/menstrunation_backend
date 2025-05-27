package com.example.demo.services;

import com.example.demo.model.VibeDto;

import java.time.LocalDate;
import java.util.List;

public interface VibeService {
    VibeDto createNewVibe(VibeDto vibeDto, long userId);

    void updateVibe(VibeDto vibeDto, long userId);

    List<VibeDto> getAllVibesInMonth(int month, int year, long userId);

    VibeDto getVibeByDate(LocalDate date, long userId);
}
