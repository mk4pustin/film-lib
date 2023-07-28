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
import ru.sber.kapustin.filmlib.dto.FilmDTO;
import ru.sber.kapustin.filmlib.model.FilmsEntity;
import ru.sber.kapustin.filmlib.service.FilmService;

@RestController
@RequestMapping(value = "/films")
@Tag(name = "Фильмы", description = "Контроллер для работы с фильмами")
public class FilmController extends GenericController<FilmsEntity, FilmDTO> {
    public FilmController(FilmService filmService) {
        super(filmService);
    }

    @Operation(description = "Добавить режиссера к фильму")
    @RequestMapping(value = "/addDirector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> addDirector(@RequestParam(value = "filmId") Long filmId,
                                               @RequestParam(value = "directorId") Long directorId) {
        return ResponseEntity.status(HttpStatus.OK).body(((FilmService) service).addDirector(filmId, directorId));
    }
}


