package ru.sber.kapustin.filmlib.repository;

import org.springframework.stereotype.Repository;
import ru.sber.kapustin.filmlib.model.UsersEntity;

@Repository
public interface UserRepository extends GenericRepository<UsersEntity> {
    UsersEntity findUserByLogin(String login);
    UsersEntity findUserByEmail(String email);

    UsersEntity findUserByChangePasswordToken(String uuid);
}

