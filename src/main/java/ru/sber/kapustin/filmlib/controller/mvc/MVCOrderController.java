package ru.sber.kapustin.filmlib.controller.mvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kapustin.filmlib.dto.OrderDTO;
import ru.sber.kapustin.filmlib.service.FilmService;
import ru.sber.kapustin.filmlib.service.OrderService;
import ru.sber.kapustin.filmlib.service.userdetails.CustomUserDetails;

@Controller
@RequestMapping("/rent")
public class MVCOrderController {
    private final OrderService orderService;
    private final FilmService filmService;

    public MVCOrderController(OrderService orderService, FilmService filmService) {
        this.orderService = orderService;
        this.filmService = filmService;
    }

    @GetMapping("/film/{filmId}")
    public String rentFilm(@PathVariable Long filmId, Model model) {
        model.addAttribute("film", filmService.getOne(filmId));
        return "rent/rentFilm";
    }

    @PostMapping("/film")
    public String rentFilm(@ModelAttribute("order") OrderDTO orderDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderDTO.setUserId(Long.valueOf(customUserDetails.getId()));
        orderService.rentFilm(orderDTO);
        return "redirect:/rent/viewAllUserFilms/" + customUserDetails.getId();
    }

    @GetMapping("/return-film/{id}")
    public String returnFilm(@PathVariable Long id) {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.returnFilm(id);
        return "redirect:/rent/viewAllUserFilms/" + customUserDetails.getId();
    }

    @GetMapping("/viewAllUserFilms/{id}")
    public String userBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            @PathVariable Long id,
            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<OrderDTO> orderDTOPage = orderService.listUserRentFilms(id, pageRequest);
        model.addAttribute("rentFilms", orderDTOPage);
        model.addAttribute("userId", id);
        return "rent/viewAllUserFilms";
    }
}



