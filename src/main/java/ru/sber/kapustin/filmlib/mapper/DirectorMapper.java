package ru.sber.kapustin.filmlib.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.kapustin.filmlib.dto.DirectorDTO;
import ru.sber.kapustin.filmlib.model.DirectorsEntity;
import ru.sber.kapustin.filmlib.model.GenericModel;
import ru.sber.kapustin.filmlib.repository.FilmRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DirectorMapper extends GenericMapper<DirectorsEntity, DirectorDTO> {
    private final FilmRepository filmRepository;

    public DirectorMapper(
            ModelMapper modelMapper,
            FilmRepository filmRepository) {
        super(DirectorsEntity.class, DirectorDTO.class, modelMapper);
        this.filmRepository = filmRepository;
    }

    @PostConstruct
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(DirectorsEntity.class, DirectorDTO.class)
                .addMappings(mapping -> mapping.skip(DirectorDTO::setFilmsId))
                .setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(DirectorDTO.class, DirectorsEntity.class)
                .addMappings(mapping -> mapping.skip(DirectorsEntity::setFilms))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(DirectorDTO source, DirectorsEntity destination) {
        if (!Objects.isNull(source.getFilmsId())) {
            destination.setFilms(filmRepository.findAllById(source.getFilmsId()));
        } else {
            destination.setFilms(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(DirectorsEntity source, DirectorDTO destination) {
        destination.setFilmsId(getIds(source));
    }

    @Override
    protected List<Long> getIds(DirectorsEntity source) {
        return Objects.isNull(source) || Objects.isNull(source.getFilms())
                ? Collections.emptyList()
                : source.getFilms().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }
}


