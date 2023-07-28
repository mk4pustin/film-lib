package ru.sber.kapustin.filmlib.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.kapustin.filmlib.controller.rest.GenericController;
import ru.sber.kapustin.filmlib.dto.OrderDTO;
import ru.sber.kapustin.filmlib.model.OrdersEntity;
import ru.sber.kapustin.filmlib.service.OrderService;

@RestController
@RequestMapping("/orders")
@Tag(
        name = "Прокат фильмов",
        description = "Контроллер для работы с прокатом фильмов")
public class OrderController extends GenericController<OrdersEntity, OrderDTO> {
    public OrderController(OrderService orderService) {
        super(orderService);
    }

    @Override
    @Operation(description = "Создать запись", method = "add")
    @RequestMapping(
            value = "/addOrder",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO newEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(newEntity));
    }
}


