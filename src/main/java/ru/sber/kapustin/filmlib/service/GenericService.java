package ru.sber.kapustin.filmlib.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.kapustin.filmlib.dto.GenericDTO;
import ru.sber.kapustin.filmlib.mapper.GenericMapper;
import ru.sber.kapustin.filmlib.model.GenericModel;
import ru.sber.kapustin.filmlib.repository.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class GenericService<E extends GenericModel, T extends GenericDTO> {

    protected final GenericRepository<E> repository;
    protected final GenericMapper<E, T> mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericService(GenericRepository<E> repository, GenericMapper<E, T> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<T> listAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public T getOne(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Данные по заданному id: " + id + " не найдены")));
    }

    public T create(T newObject){
        //newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public T update(T updatedObject){
        return mapper.toDTO(repository.save(mapper.toEntity(updatedObject)));
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}

