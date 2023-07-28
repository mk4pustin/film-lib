package ru.sber.kapustin.filmlib.service;

import ru.sber.kapustin.filmlib.dto.DirectorDTO;
import ru.sber.kapustin.filmlib.model.DirectorsEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface DirectorTestData {
    DirectorDTO DIRECTOR_DTO_1 = new DirectorDTO(
            "director_FIO1",
            "position1",
            new ArrayList<>());

    DirectorDTO DIRECTOR_DTO_2 = new DirectorDTO(
            "director_FIO2",
            "position2",
            new ArrayList<>());


    DirectorDTO DIRECTOR_DTO_3_DELETED = new DirectorDTO(
            "director_FIO3",
            "position3",
            new ArrayList<>());


    List<DirectorDTO> DIRECTOR_DTO_LIST = Arrays.asList(
            DIRECTOR_DTO_1,
            DIRECTOR_DTO_2,
            DIRECTOR_DTO_3_DELETED);


    DirectorsEntity DIRECTOR_1 = new DirectorsEntity(
            "director1",
            "position1",
            null,
            false);

    DirectorsEntity DIRECTOR_2 = new DirectorsEntity(
            "director2",
            "position2",
            null,
            false);

    DirectorsEntity DIRECTOR_3 = new DirectorsEntity(
            "director3",
            "position3",
            null,
            false);

    List<DirectorsEntity> DIRECTOR_LIST = Arrays.asList(
            DIRECTOR_1,
            DIRECTOR_2,
            DIRECTOR_3);
}


