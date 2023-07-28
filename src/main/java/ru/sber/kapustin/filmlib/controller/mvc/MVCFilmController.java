package ru.sber.kapustin.filmlib.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kapustin.filmlib.dto.FilmDTO;
import ru.sber.kapustin.filmlib.service.DirectorService;
import ru.sber.kapustin.filmlib.service.FilmService;

@Controller
@RequestMapping("/films")
public class MVCFilmController {
    private final FilmService filmService;
    private final DirectorService directorService;

    public MVCFilmController(FilmService filmService, DirectorService directorService) {
        this.filmService = filmService;
        this.directorService = directorService;
    }

    @GetMapping
    public String getAll(Model model) {
        var films = filmService.listAll();
        model.addAttribute("films", films);
        return "films/viewAllFilms";
    }

    @GetMapping("/add")
    public String create(Model model) {
        var directors = directorService.listAll();
        model.addAttribute("directors", directors);
        return "films/addFilm";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("filmForm") FilmDTO newFilm) {
        filmService.create(newFilm);
        return "redirect:/films";
    }

    @GetMapping("/addDirector")
    public String update(Model model) {
        var films = filmService.listAll();
        model.addAttribute("films", films);

        var directors = directorService.listAll();
        model.addAttribute("directors", directors);
        return "films/addDirectorToFilm";
    }

    @PostMapping("/addDirector")
    public String update(
            @RequestParam(name = "film") Long filmId,
            @RequestParam(name = "director") Long directorId) {
        filmService.addDirector(filmId, directorId);
        return "redirect:/films";
    }
}

