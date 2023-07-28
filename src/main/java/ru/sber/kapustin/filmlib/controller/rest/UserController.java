package ru.sber.kapustin.filmlib.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.kapustin.filmlib.config.jwt.JWTTokenUtil;
import ru.sber.kapustin.filmlib.dto.LoginDTO;
import ru.sber.kapustin.filmlib.dto.UserDTO;
import ru.sber.kapustin.filmlib.model.UsersEntity;
import ru.sber.kapustin.filmlib.service.GenericService;
import ru.sber.kapustin.filmlib.service.UserService;
import ru.sber.kapustin.filmlib.service.userdetails.CustomUserDetailsService;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями")
public class UserController extends GenericController<UsersEntity, UserDTO> {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final UserService userService;

    public UserController(
            GenericService<UsersEntity, UserDTO> genericService,
            CustomUserDetailsService customUserDetailsService,
            JWTTokenUtil jwtTokenUtil,
            UserService userService) {
        super(genericService);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDTO loginDTO) {
        var response = new HashMap<>();
        var foundUser = customUserDetailsService.loadUserByUsername(loginDTO.getLogin());

        if (!userService.checkPassword(loginDTO.getPassword(), foundUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации! \n Неверный пароль...");
        }
        var token = jwtTokenUtil.generateToken(foundUser);
        response.put("token", token);
        response.put("username", foundUser.getUsername());
        response.put("role", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);
    }
}