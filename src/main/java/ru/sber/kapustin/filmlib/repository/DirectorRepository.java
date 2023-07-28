package ru.sber.kapustin.filmlib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.sber.kapustin.filmlib.model.DirectorsEntity;

@Repository
public interface DirectorRepository extends GenericRepository<DirectorsEntity> {
    Page<DirectorsEntity> findAllByDirectorFIOContainsIgnoreCaseAndIsDeletedFalse(String fio, Pageable pageable);
}


