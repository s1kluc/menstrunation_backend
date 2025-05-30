package com.example.demo.services;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviceklasse welche sich um das Handling von Vibes kümmert.
 */
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
    public VibeDto createNewVibe(VibeDto vibeDto, long userId) throws HttpClientErrorException {
        Vibe vibe = Vibe.builder()
            .userId(userId)
            .anger(vibeDto.getAnger())
            .blood(vibeDto.getBlood())
            .period(vibeDto.isPeriod())
            .createdAt(LocalDate.now())
            .mood(String.join(",", vibeDto.getMood()))
            .build();
        Vibe createdVibe;
        try {
            createdVibe = this.vibeRepository.save(vibe);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return VibeDto.builder()
            .id(createdVibe.getId())
            .userId(createdVibe.getUserId())
            .anger(createdVibe.getAnger())
            .blood(createdVibe.getBlood())
            .period(createdVibe.isPeriod())
            .createdAt(dateTimeFormatter.format(createdVibe.getCreatedAt()))
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
    public void updateVibe(VibeDto vibeDto, long userId) throws HttpClientErrorException {
        Vibe foundVibe;
        try {
            foundVibe = this.vibeRepository.findVibeByIdAndUserId(vibeDto.getId(), userId);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
    public List<VibeDto> getAllVibesInMonth(int month, int year, long userId) throws HttpClientErrorException {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        List<Vibe> vibeList;
        try {
            vibeList = this.vibeRepository.findAllByCreatedAtBetweenAndUserId(
                startDate,
                endDate,
                userId
            );
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<VibeDto> vibeDtoList = new ArrayList<>();
        for (Vibe vibe : vibeList) {
            VibeDto vibeDto = VibeDto.builder()
                .id(vibe.getId())
                .userId(vibe.getUserId())
                .anger(vibe.getAnger())
                .blood(vibe.getBlood())
                .period(vibe.isPeriod())
                .mood(vibe.getMood().split(","))
                .createdAt(dateTimeFormatter.format(vibe.getCreatedAt()))
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
    public VibeDto getVibeByDate(LocalDate date, long userId) throws HttpClientErrorException {
        Vibe foundVibe;
        try {
            foundVibe = this.vibeRepository.findFirstByCreatedAtAndUserId(date, userId);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return VibeDto.builder()
            .id(foundVibe.getId())
            .userId(foundVibe.getUserId())
            .anger(foundVibe.getAnger())
            .blood(foundVibe.getBlood())
            .period(foundVibe.isPeriod())
            .mood(foundVibe.getMood().split(","))
            .createdAt(dateTimeFormatter.format(foundVibe.getCreatedAt()))
            .build();
    }

    @Override
    @Transactional
    public void deleteVibe(long id, long userId) throws HttpClientErrorException {
        try {
            this.vibeRepository.deleteVibeByIdAndUserId(id, userId);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}