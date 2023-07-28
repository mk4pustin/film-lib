package ru.sber.kapustin.filmlib.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.kapustin.filmlib.dto.UserDTO;
import ru.sber.kapustin.filmlib.model.GenericModel;
import ru.sber.kapustin.filmlib.model.UsersEntity;
import ru.sber.kapustin.filmlib.repository.OrderRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper extends GenericMapper<UsersEntity, UserDTO> {

    private final OrderRepository orderRepository;

    public UserMapper(ModelMapper modelMapper, OrderRepository orderRepository) {
        super(UsersEntity.class, UserDTO.class, modelMapper);
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(UsersEntity.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setOrdersIds)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(UserDTO.class, UsersEntity.class)
                .addMappings(m -> m.skip(UsersEntity::setOrders)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, UsersEntity destination) {
        if (!Objects.isNull(source.getOrdersIds())) {
            destination.setOrders(orderRepository.findAllById(source.getOrdersIds()));
        } else {
            destination.setOrders(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(UsersEntity source, UserDTO destination) {
        destination.setOrdersIds(getIds(source));
    }

    @Override
    protected List<Long> getIds(UsersEntity entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getOrders())
                ? null
                : entity.getOrders().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }
}

