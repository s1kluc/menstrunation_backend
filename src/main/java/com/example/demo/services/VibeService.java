package com.example.demo.services;

import com.example.demo.model.VibeDto;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface VibeService {
    VibeDto createNewVibe(VibeDto vibeDto, long userId) throws HttpClientErrorException;

    VibeDto updateVibe(VibeDto vibeDto, long userId) throws HttpClientErrorException;

    List<VibeDto> getAllVibesInMonth(int month, int year, long userId) throws HttpClientErrorException;

    VibeDto getVibeByDate(String date, long userId) throws HttpClientErrorException;

    void deleteVibe(long vibeId, long userId) throws HttpClientErrorException;
}
