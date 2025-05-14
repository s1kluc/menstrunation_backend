package com.example.demo.services;

import com.example.demo.model.VibeDto;

import java.time.LocalDateTime;
import java.util.List;

public interface VibeService {
    VibeDto createNewVibe(VibeDto vibeDto, int userId);

    VibeDto updateVibe(VibeDto vibeDto, int userId);

    List<VibeDto> getAllVibesInMonth(int month, int year);

    VibeDto getVibeByDate(LocalDateTime date);

}
