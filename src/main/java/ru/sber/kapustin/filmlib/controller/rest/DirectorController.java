package ru.sber.kapustin.filmlib.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.kapustin.filmlib.dto.DirectorDTO;
import ru.sber.kapustin.filmlib.model.DirectorsEntity;
import ru.sber.kapustin.filmlib.service.DirectorService;

@RestController
@RequestMapping(value = "/directors")
@Tag(
        name = "Режиссеры",
        description = "Контроллер для работы с режиссерами фильмов")
public class DirectorController extends GenericController<DirectorsEntity, DirectorDTO> {

    public DirectorController(DirectorService directorService) {
        super(directorService);
    }

    @Operation(description = "Добавить фильм к режиссеру")
    @RequestMapping(value = "/addFilm",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDTO> addFilm(@RequestParam(value = "filmId") Long filmId,
                                               @RequestParam(value = "directorId") Long directorId) {
        return ResponseEntity.status(HttpStatus.OK).body(((DirectorService) service).addFilm(filmId, directorId));
    }
}


