package ru.sber.kapustin.filmlib.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.kapustin.filmlib.dto.FilmDTO;
import ru.sber.kapustin.filmlib.model.FilmsEntity;
import ru.sber.kapustin.filmlib.model.GenericModel;
import ru.sber.kapustin.filmlib.repository.DirectorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FilmMapper extends GenericMapper<FilmsEntity, FilmDTO> {
    private final DirectorRepository directorRepository;

    public FilmMapper(ModelMapper modelMapper, DirectorRepository directorRepository) {
        super(FilmsEntity.class, FilmDTO.class, modelMapper);
        this.directorRepository = directorRepository;
    }

    @PostConstruct
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(FilmsEntity.class, FilmDTO.class)
                .addMappings(mapping -> mapping.skip(FilmDTO::setDirectorsIds))
                .setPostConverter(toDTOConverter());

        modelMapper.createTypeMap(FilmDTO.class, FilmsEntity.class)
                .addMappings(mapping -> mapping.skip(FilmsEntity::setDirectors))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(FilmDTO source, FilmsEntity destination) {
        if (!Objects.isNull(source.getDirectorsIds())) {
            destination.setDirectors(directorRepository.findAllById(source.getDirectorsIds()));
        } else {
            destination.setDirectors(Collections.emptyList());
        }
    }

    @Override
    protected List<Long> getIds(FilmsEntity source) {
        return Objects.isNull(source) || Objects.isNull(source.getDirectors())
                ? null
                : source.getDirectors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }

    @Override
    protected void mapSpecificFields(FilmsEntity source, FilmDTO destination) {
        destination.setDirectorsIds(getIds(source));
    }
}

