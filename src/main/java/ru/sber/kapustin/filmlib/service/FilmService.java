package ru.sber.kapustin.filmlib.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.kapustin.filmlib.dto.FilmDTO;
import ru.sber.kapustin.filmlib.mapper.FilmMapper;
import ru.sber.kapustin.filmlib.model.FilmsEntity;
import ru.sber.kapustin.filmlib.repository.DirectorRepository;
import ru.sber.kapustin.filmlib.repository.FilmRepository;

@Service
public class FilmService extends GenericService<FilmsEntity, FilmDTO> {

    DirectorRepository directorRepository;

    public FilmService(
            FilmRepository repository,
            FilmMapper mapper,
            DirectorRepository directorRepository) {
        super(repository, mapper);
        this.directorRepository = directorRepository;
    }

    public FilmDTO addDirector(Long filmId, Long directorId) {
        var director = directorRepository.findById(directorId)
                .orElseThrow(() -> new NotFoundException("Режиссер не найден"));
        var film = getOne(filmId);
        film.getDirectorsIds().add(director.getId());
        update(film);
        return film;
    }
}
