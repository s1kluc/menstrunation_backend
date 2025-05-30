package com.example.demo.services;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VibeServiceImplTest {
    @Mock
    VibeRepository vibeRepository;
    @InjectMocks
    VibeServiceImpl vibeService;
    private VibeDto vibeDto;
    private List<Vibe> vibeList;

    @BeforeEach
    void setUp() {
        this.vibeList = new ArrayList<>();
        vibeDto = new VibeDto(
            1L,
            1L,
            12L,
            true,
            1,
            new String[]{"test1", "test2", "test3", "test4"},
            "2025-01-01"
        );
        Vibe vibe1 = new Vibe(
            1L,
            1L,
            12L,
            true,
            1,
            "test1,test2,test3,test4",
            LocalDate.of(2025, 1, 1)
        );
        Vibe vibe2 = new Vibe(
            2L,
            1L,
            12L,
            true,
            1,
            "happy,clappy,sad",
            LocalDate.of(2025, 1, 3)
        );
        this.vibeList.add(vibe1);
        this.vibeList.add(vibe2);
    }

    @Test
    void createNewVibeSuccess() {
        given(vibeRepository.save(any(Vibe.class))).willReturn(vibeList.getFirst());
        VibeDto result = this.vibeService.createNewVibe(vibeDto, 1L);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(vibeDto);
    }

    @Test
    void updateVibe() {
        VibeDto updatedVibeDto = new VibeDto(
            1L,
            1L,
            12L,
            true,
            1,
            new String[]{"bla1", "bla2", "bla3"},
            "2025-01-01"
        );
        given(vibeRepository.findVibeByIdAndUserId(anyLong(), anyLong())).willReturn(vibeList.getFirst());
        vibeService.updateVibe(updatedVibeDto, 1L);
        verify(vibeRepository, times(1)).save(any(Vibe.class));
    }

    @Test
    void getAllVibesInMonth() {

        given(vibeRepository.findAllByCreatedAtBetweenAndUserId(any(LocalDate.class), any(LocalDate.class), any(Long.class))).willReturn(
            this.vibeList);

        List<VibeDto> results = this.vibeService.getAllVibesInMonth(1, 2025, 1L);

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.getFirst().getAnger()).isEqualTo(vibeList.getFirst().getAnger());
        assertThat(results.getFirst().getId()).isEqualTo(vibeList.getFirst().getId());
        assertThat(results.getFirst().getMood()).isEqualTo(vibeList.getFirst().getMood().split(","));
        assertThat(results.getFirst().getBlood()).isEqualTo(vibeList.getFirst().getBlood());
        assertThat(results.getFirst().getUserId()).isEqualTo(vibeList.getFirst().getUserId());
    }

    @Test
    void getVibeByDate() {
        given(vibeRepository.findFirstByCreatedAtAndUserId(any(LocalDate.class), any(Long.class))).willReturn(vibeList.getFirst());
        VibeDto result = this.vibeService.getVibeByDate("2025-01-01", 1L);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(vibeDto);
    }
}