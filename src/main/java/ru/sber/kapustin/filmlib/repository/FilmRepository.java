package ru.sber.kapustin.filmlib.repository;

import org.springframework.stereotype.Repository;
import ru.sber.kapustin.filmlib.model.FilmsEntity;

@Repository
public interface FilmRepository extends GenericRepository<FilmsEntity>{
}

