package com.example.demo.controller;

import com.example.demo.model.VibeDto;
import com.example.demo.services.VibeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controllerschnittstelle für die Behandlung von Vibe-Objekten
 */
@RestController
@RequestMapping(value = "/menstrunation/api/vibe")
@RequiredArgsConstructor
public class VibeController {
    private final VibeService vibeService;

    /**
     * Schnittstelle zur Erstellung eines neuen Vibe-Objektes
     *
     * @param vibeDto DTO-Objekt mit Informationen
     * @return das erstellte Vibe-Objekt
     */
    @PostMapping
    public VibeDto createNewVibe(@RequestBody VibeDto vibeDto) {
        return vibeService.createNewVibe(vibeDto, 1);
    }

    /**
     * Liefert alle Vibe-Objekte innerhalb eines Monats zurück
     *
     * @param year  das gewünschte Jahr
     * @param month der gewünschte Monat
     * @return die gefundenen Vibe-Objekte
     */
    @GetMapping(value = "/monthly")
    public List<VibeDto> getAllVibesByMonthAndYear(
        @RequestParam(name = "year") int year,
        @RequestParam(name = "month") int month
    ) {
        return this.vibeService.getAllVibesInMonth(month, year, 1);
    }

    /**
     * Liefert ein Vibe-Objekt abhängig vom Datum zurück
     *
     * @param date das gewünschte Datum
     * @return das gefundene Vibe-Objekt
     */
    @GetMapping()
    public VibeDto getVibeByDate(
        @RequestParam(name = "date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {

        return this.vibeService.getVibeByDate(date, 1);
    }

    /**
     * Aktualisiert ein bestehendes Vibe-Objekt
     *
     * @param vibeDto das DTO-Objekt mit Informationen
     */
    @PutMapping
    public void updateVibe(@RequestBody VibeDto vibeDto) {
        this.vibeService.updateVibe(vibeDto, 1);
    }
}
