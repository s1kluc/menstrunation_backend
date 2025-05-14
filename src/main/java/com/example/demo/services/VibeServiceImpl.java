package com.example.demo.services;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VibeServiceImpl implements VibeService {
    private VibeRepository vibeRepository;

    @Override
    public VibeDto createNewVibe(VibeDto vibeDto, int userId) {
        Vibe vibe = new Vibe();
        vibe.setMood(String.join(",", vibeDto.getMood()));
        vibe.setAnger(userId);
        vibe.setBlood(vibe.getBlood());
        vibe.setDate(vibe.getDate());
        vibe.setPeriod(vibeDto.isPeriod());

        Vibe savedVibe = vibeRepository.save(vibe);

        return new VibeDto(
            savedVibe.getId(),
            savedVibe.getUserId(),
            savedVibe.getDate(),
            savedVibe.getAnger(),
            savedVibe.isPeriod(),
            savedVibe.getBlood(),
            savedVibe.getMood().split(",")
        );
    }

    @Override
    public VibeDto updateVibe(VibeDto vibeDto, int userId) {
        Vibe foundVibe = vibeRepository.findVibeByIdEqualsAndUserIdEquals(vibeDto.getId(), userId);

        foundVibe.setPeriod(vibeDto.isPeriod());
        foundVibe.setMood(String.join(",", vibeDto.getMood()));
        foundVibe.setAnger(vibeDto.getAnger());
        foundVibe.setBlood(vibeDto.getBlood());
        foundVibe.setDate(vibeDto.getDate());

        Vibe updatedVibe = vibeRepository.save(foundVibe);

        return new VibeDto(
            updatedVibe.getId(),
            updatedVibe.getUserId(),
            updatedVibe.getDate(),
            updatedVibe.getAnger(),
            updatedVibe.isPeriod(),
            updatedVibe.getBlood(),
            updatedVibe.getMood().split(",")
        );
    }

    @Override
    public List<VibeDto> getAllVibesInMonth(int month, int year) {
        List<Vibe> vibeList = vibeRepository.findAllByDateMonthValueAndDateYear(month, year);
        List<VibeDto> vibeDtoList = new ArrayList<VibeDto>();
        for (Vibe vibe : vibeList) {
            VibeDto vibeDto = new VibeDto(
                vibe.getId(),
                vibe.getUserId(),
                vibe.getDate(),
                vibe.getAnger(),
                vibe.isPeriod(),
                vibe.getBlood(),
                vibe.getMood().split(",")
            );
            vibeDtoList.add(vibeDto);
        }
        return vibeDtoList;
    }

    @Override
    public VibeDto getVibeByDate(LocalDateTime date) {
        Vibe vibe = vibeRepository.findVibeByDate(date);
        return new VibeDto(
            vibe.getId(),
            vibe.getUserId(),
            vibe.getDate(),
            vibe.getAnger(),
            vibe.isPeriod(),
            vibe.getBlood(),
            vibe.getMood().split(",")
        );
    }
}
