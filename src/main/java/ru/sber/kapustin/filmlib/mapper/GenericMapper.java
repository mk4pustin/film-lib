package ru.sber.kapustin.filmlib.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.kapustin.filmlib.dto.GenericDTO;
import ru.sber.kapustin.filmlib.model.GenericModel;

import java.util.List;
import java.util.Objects;


@Component
public abstract class GenericMapper<E extends GenericModel, T extends GenericDTO> implements Mapper<E, T> {
    private final Class<E> entityClass;
    private final Class<T> dtoClass;
    protected final ModelMapper modelMapper;

    public GenericMapper(
            Class<E> entityClass,
            Class<T> dtoClass,
            ModelMapper modelMapper) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.modelMapper = modelMapper;
    }

    @Override
    public E toEntity(T dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, entityClass);
    }

    @Override
    public T toDTO(E entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, dtoClass);
    }

    @Override
    public List<E> toEntities(List<T> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    @Override
    public List<T> toDTOs(List<E> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    protected Converter<T, E> toEntityConverter() {
        return context -> {
            T source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected Converter<E, T> toDTOConverter() {
        return context -> {
            E source = context.getSource();
            T destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected abstract void mapSpecificFields(T source, E destination);

    protected abstract void mapSpecificFields(E source, T destination);


    @PostConstruct
    protected abstract void setupMapper();

    protected abstract List<Long> getIds(E entity);
}

