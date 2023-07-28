package ru.sber.kapustin.filmlib.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.kapustin.filmlib.dto.RoleDTO;
import ru.sber.kapustin.filmlib.dto.UserDTO;
import ru.sber.kapustin.filmlib.mapper.FilmMapper;
import ru.sber.kapustin.filmlib.mapper.GenericMapper;
import ru.sber.kapustin.filmlib.model.UsersEntity;
import ru.sber.kapustin.filmlib.repository.GenericRepository;
import ru.sber.kapustin.filmlib.repository.OrderRepository;
import ru.sber.kapustin.filmlib.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService extends GenericService<UsersEntity, UserDTO> {
    OrderRepository orderRepository;
    FilmMapper filmMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(
            GenericRepository<UsersEntity> repository,
            GenericMapper<UsersEntity, UserDTO> mapper,
            OrderRepository orderRepository, FilmMapper filmMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository, mapper);
        this.orderRepository = orderRepository;
        this.filmMapper = filmMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO create(UserDTO newObject) {
        var roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        //newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public UserDTO getUserByLogin(String login) {
        return mapper.toDTO(((UserRepository) repository).findUserByLogin(login));
    }

    public UserDTO getUserByEmail(String email) {
        return mapper.toDTO(((UserRepository) repository).findUserByEmail(email));
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }

    public void changePassword(String uuid, String password) {
        var userDTO = mapper.toDTO(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        userDTO.setChangePasswordToken(null);
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        update(userDTO);
    }
}


