package com.example.demo.controller;

import com.example.demo.model.VibeDto;
import com.example.demo.services.VibeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/menstrunation/api/vibe")
@RequiredArgsConstructor
public class VibeController {

    private final VibeService vibeService;

    @PostMapping
    public VibeDto createNewVibe(@RequestBody VibeDto vibeDto) {
        return vibeService.createNewVibe(vibeDto, 1);
    }

    @GetMapping(value = "/by-month")
    public List<VibeDto> getAllVibesByMonthAndYear(@RequestParam int month,
                                                   @RequestParam int year) {
        return this.vibeService.getAllVibesInMonth(month, year, 1);
    }

    @GetMapping
    public VibeDto getVibeByDate(@RequestParam LocalDateTime date) {
        return this.vibeService.getVibeByDate(date, 1);
    }

    @PutMapping
    public void updateVibe(@RequestBody VibeDto vibeDto) {
        this.vibeService.updateVibe(vibeDto, 1);
    }
}
