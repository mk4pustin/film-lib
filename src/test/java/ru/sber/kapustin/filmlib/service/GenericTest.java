package ru.sber.kapustin.filmlib.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.sber.kapustin.filmlib.dto.GenericDTO;
import ru.sber.kapustin.filmlib.mapper.GenericMapper;
import ru.sber.kapustin.filmlib.model.GenericModel;
import ru.sber.kapustin.filmlib.repository.GenericRepository;
import ru.sber.kapustin.filmlib.service.userdetails.CustomUserDetails;

public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {
    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                CustomUserDetails.builder()
                        .username("USER"),
                null,
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected abstract void getAll();
    protected abstract void getOne();
    protected abstract void create();
    protected abstract void update();
    //    protected abstract void delete();
    //    protected abstract void restore();
    protected abstract void getAllNotDeleted();

}

