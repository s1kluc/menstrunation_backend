package com.example.demo.controller;

import com.example.demo.model.VibeDto;
import com.example.demo.services.VibeService;
import com.example.demo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    private final JwtUtils jwtUtils;

    /**
     * Schnittstelle zur Erstellung eines neuen Vibe-Objektes
     *
     * @param vibeDto DTO-Objekt mit Informationen
     * @return das erstellte Vibe-Objekt
     */
    @PostMapping
    public VibeDto createNewVibe(
        @RequestBody VibeDto vibeDto,
        @RequestHeader("Authorization") String bearerToken
    ) throws HttpClientErrorException {
        return vibeService.createNewVibe(
            vibeDto, jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken)).get("userId", Long.class
            )
        );
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
        @RequestParam(name = "month") int month,
        @RequestHeader("Authorization") String bearerToken
    ) throws HttpClientErrorException {
        return this.vibeService.getAllVibesInMonth(
            month, year, this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken)).get("userId", Long.class)
        );
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
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestHeader("Authorization") String bearerToken
    ) throws HttpClientErrorException {
        return this.vibeService.getVibeByDate(
            date,
            this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken)).get("userId", Long.class)
        );
    }

    /**
     * Aktualisiert ein bestehendes Vibe-Objekt
     *
     * @param vibeDto das DTO-Objekt mit Informationen
     */
    @PutMapping
    public void updateVibe(
        @RequestBody VibeDto vibeDto,
        @RequestHeader("Authorization") String bearerToken
    ) throws HttpClientErrorException {
        this.vibeService.updateVibe(vibeDto, this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken)).get("userid", Long.class));
    }

    /**
     * Löscht ein bestehendes Vibeobjekt aus der Datenbank.
     *
     * @param vibeDto     das zu löschende Vibeobjekt
     * @param bearerToken ein valider Bearer token
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @DeleteMapping
    public ResponseEntity<String> deleteVibe(@RequestBody VibeDto vibeDto, @RequestHeader("Authorization") String bearerToken)
        throws HttpClientErrorException {
        try {
            this.vibeService.deleteVibe(
                vibeDto.getId(),
                this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken)).get("userid", Long.class)
            );
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403), e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
