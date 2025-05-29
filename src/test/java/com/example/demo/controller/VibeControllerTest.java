package com.example.demo.controller;

import com.example.demo.Utils.TestBase;
import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import com.example.demo.repository.VibeRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VibeControllerTest extends TestBase {
    @Autowired
    private Flyway flyway;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VibeRepository vibeRepository;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        this.init();
    }

    @Test
    void createNewVibe() throws Exception {
        VibeDto vibeDto = new VibeDto();
        vibeDto.setMood(new String[]{"newMood"});
        vibeDto.setAnger(1L);
        vibeDto.setBlood(1);
        vibeDto.setPeriod(false);
        vibeDto.setUserId(3L);

        MvcResult mvcResult = this.mockMvc.perform(post("/menstrunation/api/vibe")
                                                       .content("""
                                                                    {
                                                                      "anger": 29292,
                                                                      "blood": 1,
                                                                      "mood": [
                                                                        "neue",
                                                                        "bla",
                                                                        "kisten"
                                                                      ],
                                                                      "period": false,
                                                                        "userId": 1
                                                                    }""")
        ).andReturn();

        assertThat(mvcResult).isNotNull();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("test");
    }

    @Test
    void getAllVibesByMonthAndYear() {
    }

    @Test
    void getVibeByDate() {
    }

    @Test
    void updateVibe() {
    }

    private void init() {
        Vibe vibe1 = new Vibe();
        vibe1.setUserId(1L);
        vibe1.setMood("blablabla");
        vibe1.setAnger(12L);
        vibe1.setBlood(1);
        vibe1.setPeriod(true);
        vibe1.setCreatedAt(LocalDate.of(2025, 1, 1));
        this.vibeRepository.save(vibe1);

        Vibe vibe2 = new Vibe();
        vibe2.setUserId(2L);
        vibe2.setMood("juppy,duppy,fuppy");
        vibe2.setAnger(2L);
        vibe2.setBlood(1);
        vibe2.setPeriod(false);
        vibe2.setCreatedAt(LocalDate.of(2025, 2, 1));
        this.vibeRepository.save(vibe2);

        Vibe vibe3 = new Vibe();
        vibe3.setUserId(1L);
        vibe3.setMood("juppy,duppy,ugly,horty");
        vibe3.setAnger(234L);
        vibe3.setBlood(0);
        vibe3.setPeriod(true);
        vibe3.setCreatedAt(LocalDate.of(2025, 1, 1));
        this.vibeRepository.save(vibe3);
    }
}