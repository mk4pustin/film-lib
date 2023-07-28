package ru.sber.kapustin.filmlib.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO extends GenericDTO {
    private String title;
    private String description;
}

