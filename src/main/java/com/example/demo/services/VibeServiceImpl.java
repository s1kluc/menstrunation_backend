package com.example.demo.services;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VibeServiceImpl implements VibeService {
    private final VibeRepository vibeRepository;

    /**
     * Erstellt ein neues Vibe-Objekt
     *
     * @param vibeDto das DTO-Objekt mit Informationen
     * @param userId  die User-Id
     * @return das erstellte Vibe-Objekt
     */
    @Override
    public VibeDto createNewVibe(VibeDto vibeDto, long userId) {
        Vibe vibe = Vibe.builder()
            .userId(userId)
            .anger(vibeDto.getAnger())
            .blood(vibeDto.getBlood())
            .period(vibeDto.isPeriod())
            .createdAt(LocalDate.now())
            .mood(String.join(",", vibeDto.getMood()))
            .build();

        //Hier noch OptionalCheck einbauen
        Optional<Vibe> optionalVibe = Optional.of(this.vibeRepository.save(vibe));

        Vibe createdVibe = optionalVibe.get();

        return VibeDto.builder()
            .id(createdVibe.getId())
            .userId(createdVibe.getUserId())
            .anger(createdVibe.getAnger())
            .blood(createdVibe.getBlood())
            .period(createdVibe.isPeriod())
            .createdAt(createdVibe.getCreatedAt())
            .mood(createdVibe.getMood().split(","))
            .build();
    }

    /**
     * Aktualisiert ein bestehendes Vibe-Objekt
     *
     * @param vibeDto das DTO-Objekt mit Informationen
     * @param userId  die User-Id
     */
    @Override
    public void updateVibe(VibeDto vibeDto, long userId) {
        Optional<Vibe> optionalVibe = Optional.of(this.vibeRepository.findVibeByIdAndUserId(vibeDto.getId(), userId));

        Vibe foundVibe = optionalVibe.get();

        foundVibe.setPeriod(vibeDto.isPeriod());
        foundVibe.setMood(String.join(",", vibeDto.getMood()));
        foundVibe.setAnger(vibeDto.getAnger());
        foundVibe.setBlood(vibeDto.getBlood());

        this.vibeRepository.save(foundVibe);
    }

    /**
     * Gibt alle Vibe-Objekte innerhalb eines Monats zurück
     *
     * @param month  der Monat als Zahl
     * @param year   das Jahr als Zahl
     * @param userId die User-Id
     * @return die gefundenen Vibe-Objekte
     */
    @Override
    public List<VibeDto> getAllVibesInMonth(int month, int year, long userId) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        Optional<List<Vibe>> optionalVibes = Optional.of(this.vibeRepository.findAllByCreatedAtBetweenAndUserId(
            startDate,
            endDate,
            userId
        ));

        List<Vibe> vibeList = optionalVibes.get();

        List<VibeDto> vibeDtoList = new ArrayList<>();
        for (Vibe vibe : vibeList) {
            VibeDto vibeDto = VibeDto.builder()
                .id(vibe.getId())
                .userId(vibe.getUserId())
                .anger(vibe.getAnger())
                .blood(vibe.getBlood())
                .period(vibe.isPeriod())
                .mood(vibe.getMood().split(","))
                .createdAt(vibe.getCreatedAt())
                .build();
            vibeDtoList.add(vibeDto);
        }
        return vibeDtoList;
    }

    /**
     * Liefert nur ein Vibe-Objekt eines bestimmten Datums zurück
     *
     * @param date   das gewünschte Datum
     * @param userId die User-Id
     * @return das gefundene Vibe-Objekt
     */
    @Override
    public VibeDto getVibeByDate(LocalDate date, long userId) {
        Optional<Vibe> optionalVibe = Optional.of(this.vibeRepository.findVibeByCreatedAtAndUserId(date, userId));
        Vibe vibe = optionalVibe.get();

        return VibeDto.builder()
            .id(vibe.getId())
            .userId(vibe.getUserId())
            .anger(vibe.getAnger())
            .blood(vibe.getBlood())
            .period(vibe.isPeriod())
            .mood(vibe.getMood().split(","))
            .createdAt(vibe.getCreatedAt())
            .build();
    }
}