package ru.sber.kapustin.filmlib.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class DirectorDTO extends GenericDTO {
    private String directorFIO;
    private String position;
    private List<Long> filmsId;
    private boolean isDeleted;

    public DirectorDTO(String directorFIO, String position, List<Long> filmsId) {
        this.directorFIO = directorFIO;
        this.position = position;
        this.filmsId = filmsId;
        this.isDeleted = false;
    }
}

