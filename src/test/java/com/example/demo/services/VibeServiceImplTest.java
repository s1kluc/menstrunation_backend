package com.example.demo.services;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VibeServiceImplTest {
    @Mock
    VibeRepository vibeRepository;
    @InjectMocks
    VibeServiceImpl vibeService;

    private VibeDto vibeDto;
    private Vibe vibe;

    @BeforeEach
    void setUp() {
        vibeRepository = Mockito.mock(VibeRepository.class);
        String[] moodArray = new String[]{"test1", "test2", "test3", "test4"};
        vibeDto = new VibeDto(
            1L,
            1L,
            12,
            true,
            1,
            moodArray,
            LocalDate.of(2025, 1, 1)
        );
        vibe = new Vibe(
            1L,
            1L,
            12,
            true,
            1,
            "test1,test2,test3,test4",
            LocalDate.of(2025, 1, 1)
        );
    }

    @Test
    void createNewVibeSuccess() {
        given(vibeRepository.save(any(Vibe.class))).willReturn(vibe);
        VibeDto result = this.vibeService.createNewVibe(vibeDto, 1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.vibeDto);
    }

    @Test
    void updateVibe() {
    }

    @Test
    void getAllVibesInMonth() {
    }

    @Test
    void getVibeByDate() {
    }
}