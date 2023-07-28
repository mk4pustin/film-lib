package ru.sber.kapustin.filmlib.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.kapustin.filmlib.dto.OrderDTO;
import ru.sber.kapustin.filmlib.model.OrdersEntity;
import ru.sber.kapustin.filmlib.repository.FilmRepository;
import ru.sber.kapustin.filmlib.repository.UserRepository;

import java.util.List;

@Component
public class OrderMapper extends GenericMapper<OrdersEntity, OrderDTO> {

    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public OrderMapper(ModelMapper modelMapper, FilmRepository filmRepository, UserRepository userRepository) {
        super(OrdersEntity.class, OrderDTO.class, modelMapper);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Override
    public void setupMapper() {
        super.modelMapper.createTypeMap(OrdersEntity.class, OrderDTO.class)
                .addMappings(m -> m.skip(OrderDTO::setUserId))
                .addMappings(m -> m.skip(OrderDTO::setFilmId))
                .setPostConverter(toDTOConverter());

        super.modelMapper.createTypeMap(OrderDTO.class, OrdersEntity.class)
                .addMappings(m -> m.skip(OrdersEntity::setUser))
                .addMappings(m -> m.skip(OrdersEntity::setFilm))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(OrderDTO source, OrdersEntity destination) {
        destination.setFilm(filmRepository.findById(source.getFilmId())
                .orElseThrow(() -> new NotFoundException("Фильм не найден")));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(OrdersEntity source, OrderDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setFilmId(source.getFilm().getId());
    }

    @Override
    protected List<Long> getIds(OrdersEntity entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}

