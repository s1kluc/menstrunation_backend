package com.example.demo.repository;

import com.example.demo.model.Vibe;
import com.example.demo.model.VibeDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@EnableJpaRepositories
public interface VibeRepository extends CrudRepository<Vibe, Long> {
    Vibe findVibeByIdEqualsAndUserIdEquals(int id, int userId);

    List<Vibe> findAllByDateMonthValueAndDateYear(int dateMonthValue, int dateYear);

    Vibe findVibeByDate(LocalDateTime date);
}
