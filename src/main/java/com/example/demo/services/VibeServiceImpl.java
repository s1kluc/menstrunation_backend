package com.example.demo.services;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VibeServiceImpl implements VibeService {

    private final VibeRepository vibeRepository;

    @Override
    public VibeDto createNewVibe(VibeDto vibeDto, long userId) {
        Vibe vibe = Vibe.builder()
                .userId(userId)
                .anger(vibeDto.getAnger())
                .blood(vibeDto.getBlood())
                .period(vibeDto.isPeriod())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .mood(String.join(",", vibeDto.getMood()))
                .build();

        Vibe createdVibe = this.vibeRepository.save(vibe);

        return VibeDto.builder()
                .id(createdVibe.getId())
                .userId(createdVibe.getUserId())
                .anger(createdVibe.getAnger())
                .blood(createdVibe.getBlood())
                .period(createdVibe.isPeriod())
                .updatedAt(createdVibe.getUpdatedAt())
                .createdAt(createdVibe.getCreatedAt())
                .mood(createdVibe.getMood().split(","))
                .build();
    }

    @Override
    public void updateVibe(VibeDto vibeDto, long userId) {
        Optional<Vibe> foundVibeOptional = Optional.ofNullable(this.vibeRepository.findVibeByIdEqualsAndUserIdEquals(vibeDto.getId(), userId));
        //optionalCheck
        Vibe foundVibe = foundVibeOptional.get();

        foundVibe.setPeriod(vibeDto.isPeriod());
        foundVibe.setMood(String.join(",", vibeDto.getMood()));
        foundVibe.setAnger(vibeDto.getAnger());
        foundVibe.setBlood(vibeDto.getBlood());
        foundVibe.setUpdatedAt(vibeDto.getUpdatedAt());

        this.vibeRepository.save(foundVibe);
    }

    @Override
    public List<VibeDto> getAllVibesInMonth(int month, int year, long userId) {
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);
        Optional<List<Vibe>> vibeListOptional = Optional.ofNullable(this.vibeRepository.findAllByCreatedAtBetweenAndUserId(start, end, userId));

        //optionalCheck
        List<Vibe> vibeList = vibeListOptional.get();
        List<VibeDto> vibeDtoList = new ArrayList<>();
        for (Vibe vibe : vibeList) {
            VibeDto vibeDto = VibeDto.builder()
                    .id(vibe.getId())
                    .userId(vibe.getUserId())
                    .anger(vibe.getAnger())
                    .blood(vibe.getBlood())
                    .period(vibe.isPeriod())
                    .mood(vibe.getMood().split(","))
                    .updatedAt(vibe.getUpdatedAt())
                    .createdAt(vibe.getCreatedAt())
                    .build();
            vibeDtoList.add(vibeDto);
        }
        return vibeDtoList;
    }

    @Override
    public VibeDto getVibeByDate(LocalDateTime date, long userId) {
        LocalDateTime specificDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), date.getHour(), date.getMinute());
        Optional<Vibe> optionalVibe = Optional.ofNullable(this.vibeRepository.findVibeByCreatedAtEqualsAndUserId(specificDate, userId));

        //optionalCheck
        Vibe vibe = optionalVibe.get();

        return VibeDto.builder()
                .id(vibe.getId())
                .userId(vibe.getUserId())
                .anger(vibe.getAnger())
                .blood(vibe.getBlood())
                .period(vibe.isPeriod())
                .mood(vibe.getMood().split(","))
                .createdAt(vibe.getCreatedAt())
                .updatedAt(vibe.getUpdatedAt())
                .build();
    }
}
