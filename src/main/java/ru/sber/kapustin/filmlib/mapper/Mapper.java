package ru.sber.kapustin.filmlib.mapper;

import ru.sber.kapustin.filmlib.dto.GenericDTO;
import ru.sber.kapustin.filmlib.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, T extends GenericDTO> {
    E toEntity(T dto);
    T toDTO(E entity);
    List<E> toEntities(List<T> dtos);
    List<T> toDTOs(List<E> entities);
}

