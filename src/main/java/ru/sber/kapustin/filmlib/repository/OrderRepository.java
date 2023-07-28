package ru.sber.kapustin.filmlib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.sber.kapustin.filmlib.model.OrdersEntity;

@Repository
public interface OrderRepository extends GenericRepository<OrdersEntity> {
    Page<OrdersEntity> getOrderByUserId(Long id, Pageable pageRequest);
}

