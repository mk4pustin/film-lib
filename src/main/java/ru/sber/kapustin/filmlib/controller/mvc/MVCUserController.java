package ru.sber.kapustin.filmlib.controller.mvc;

import jakarta.websocket.server.PathParam;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.sber.kapustin.filmlib.constants.UserRoles;
import ru.sber.kapustin.filmlib.dto.UserDTO;
import ru.sber.kapustin.filmlib.service.UserService;
import ru.sber.kapustin.filmlib.service.userdetails.CustomUserDetails;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class MVCUserController {
    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewAllUsers(Model model) {
        List<UserDTO> users = userService.listAll();
        model.addAttribute("users", users);
        return "/users/viewAllUsers";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO, BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(UserRoles.ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой e-mail уже существует");
            return "registration";
        }
        userService.create(userDTO);
        return "redirect:login";
    }

    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "users/rememberPassword";
    }

    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            throw new NotFoundException("Пользователь не найден.");
        } else {
            // работа с изменением пароля по почте
            return "redirect:/login";
        }
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid, Model model) {
        model.addAttribute("uuid", uuid);
        //страница с изменением
        return "users/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @PathParam(value = "uuid") String uuid,
            @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String getUserProfile(Model model) {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getOne(Long.valueOf(customUserDetails.getId()));
        model.addAttribute("user", user);
        return "/users/viewUser";
    }

    @GetMapping("/update")
    public String update(Model model) {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getOne(Long.valueOf(customUserDetails.getId()));
        model.addAttribute("user", user);
        return "/users/changeUserProfile" ;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("userUpdateForm") UserDTO userDTO) {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDTO.setRole(userService.getOne(Long.valueOf(customUserDetails.getId())).getRole());
        userService.update(userDTO);
        return "redirect:/users/profile/" + customUserDetails.getId();
    }
}



