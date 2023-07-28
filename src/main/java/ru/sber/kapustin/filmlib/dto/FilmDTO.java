package ru.sber.kapustin.filmlib.dto;

import lombok.*;
import ru.sber.kapustin.filmlib.model.Genre;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FilmDTO extends GenericDTO {
    private String title;
    private Integer premierYear;
    private String country;
    private Genre genre;
    private Integer amount;
    private List<Long> directorsIds;
}

