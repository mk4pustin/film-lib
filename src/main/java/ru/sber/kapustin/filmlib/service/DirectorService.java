package ru.sber.kapustin.filmlib.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.kapustin.filmlib.dto.DirectorDTO;
import ru.sber.kapustin.filmlib.mapper.DirectorMapper;
import ru.sber.kapustin.filmlib.model.DirectorsEntity;
import ru.sber.kapustin.filmlib.repository.DirectorRepository;
import ru.sber.kapustin.filmlib.repository.FilmRepository;

import java.util.List;

@Service
public class DirectorService extends GenericService<DirectorsEntity, DirectorDTO> {

    public final FilmRepository filmRepository;

    public DirectorService(
            DirectorRepository repository,
            DirectorMapper mapper,
            FilmRepository filmRepository) {
        super(repository, mapper);
        this.filmRepository = filmRepository;
    }

    public DirectorDTO addFilm(Long filmId, Long directorId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));

        var director = getOne(directorId);
        director.getFilmsId().add(film.getId());
        update(director);
        return director;
    }

    public void delete(final Long id) {
        var directorToDelete = repository.findById(id).orElseThrow(() -> new NotFoundException("Директор не найден"));
        directorToDelete.setDeleted(true);
        repository.save(directorToDelete);
    }

    public Page<DirectorDTO> searchDirectors(String fio, Pageable pageable) {
        Page<DirectorsEntity> directors = ((DirectorRepository) repository).findAllByDirectorFIOContainsIgnoreCaseAndIsDeletedFalse(fio, pageable);
        List<DirectorDTO> result = mapper.toDTOs(directors.getContent());
        return new PageImpl<>(result, pageable, directors.getTotalElements());
    }
}

