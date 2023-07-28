package ru.sber.kapustin.filmlib.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO extends GenericDTO {
    private Long userId;
    private Long filmId;
    private FilmDTO filmDTO;
    private LocalDate rentDate;
    private Integer rentPeriod;
    private LocalDate returnDate;
    private Boolean returned;
}

