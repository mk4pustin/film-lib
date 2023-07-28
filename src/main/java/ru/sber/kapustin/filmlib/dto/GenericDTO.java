package ru.sber.kapustin.filmlib.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GenericDTO {
    private Long id;
    private LocalDateTime createdWhen;
}

