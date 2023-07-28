package ru.sber.kapustin.filmlib.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO extends GenericDTO{
    private String login;
    private String password;
    private LocalDate birthDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private RoleDTO role;
    private String changePasswordToken;
    private List<Long> ordersIds;
    private boolean isDeleted;
}

