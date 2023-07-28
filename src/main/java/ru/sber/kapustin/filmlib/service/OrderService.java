package ru.sber.kapustin.filmlib.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.kapustin.filmlib.dto.OrderDTO;
import ru.sber.kapustin.filmlib.mapper.OrderMapper;
import ru.sber.kapustin.filmlib.model.OrdersEntity;
import ru.sber.kapustin.filmlib.repository.FilmRepository;
import ru.sber.kapustin.filmlib.repository.OrderRepository;
import ru.sber.kapustin.filmlib.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class OrderService extends GenericService<OrdersEntity, OrderDTO> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    private final FilmService filmService;

    public static final Long DEFAULT_RENT_PERIOD = 14L;

    public OrderService(
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            FilmRepository filmRepository,
            UserRepository userRepository,
            FilmService filmService) {
        super(orderRepository, orderMapper);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.filmService = filmService;
    }

    @Override
    public OrderDTO create(OrderDTO newObject){
        userRepository.findById(newObject.getUserId())
                .orElseThrow(()  -> new NotFoundException("Пользователь не найден"));

        filmRepository.findById(newObject.getFilmId())
                .orElseThrow(()  -> new NotFoundException("Фильм не найден"));

        //newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public OrderDTO rentFilm(OrderDTO orderDTO) {
        var filmDTO = filmService.getOne(orderDTO.getFilmId());
        filmDTO.setAmount(filmDTO.getAmount() - 1);
        filmService.update(filmDTO);
        var rentPeriod = orderDTO.getRentPeriod() != null ? orderDTO.getRentPeriod() : DEFAULT_RENT_PERIOD;
        orderDTO.setRentDate(LocalDate.now());
        orderDTO.setReturned(false);
        orderDTO.setRentPeriod((int) rentPeriod);
        orderDTO.setReturnDate(LocalDate.now().plusDays(rentPeriod));
        return mapper.toDTO(repository.save(mapper.toEntity(orderDTO)));
    }

    public Page<OrderDTO> listUserRentFilms(final Long id, final Pageable pageRequest) {
        Page<OrdersEntity> objects = ((OrderRepository) repository).getOrderByUserId(id, pageRequest);
        List<OrderDTO> results = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(results, pageRequest, objects.getTotalElements());
    }

    public void returnFilm(Long id) {
        var orderDTO = getOne(id);
        orderDTO.setReturned(true);
        orderDTO.setReturnDate(LocalDate.now());
        var filmDTO = filmService.getOne(orderDTO.getFilmId());
        filmDTO.setAmount(filmDTO.getAmount() + 1);
        update(orderDTO);
        filmService.update(filmDTO);
    }
}

