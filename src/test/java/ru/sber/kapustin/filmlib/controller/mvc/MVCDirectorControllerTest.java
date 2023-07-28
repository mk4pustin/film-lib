package ru.sber.kapustin.filmlib.controller.mvc;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sber.kapustin.filmlib.dto.DirectorDTO;
import ru.sber.kapustin.filmlib.service.DirectorService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class MVCDirectorControllerTest extends CommonTestMVC {

    @Autowired
    private DirectorService directorService;

    private final DirectorDTO directorDTO = new DirectorDTO("MVC_TestDirectorFio", "Test Position", new ArrayList<>());
    private final DirectorDTO directorDTOUpdated = new DirectorDTO("MVC_TestDirectorFio_UPDATED", "Test Position", new ArrayList<>());


    /**
     * Метод, тестирующий просмотр всех режиссеров через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном просмотре
     *
     * @throws Exception - любая ошибка
     */
    @Override
    @Test
    @DisplayName("Просмотр всех режиссеров через MVC контроллер")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void listAll() throws Exception {
        log.info("Тест просмотра режиссеров MVC начат!");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/directors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("directors/viewAllDirectors"))
                .andExpect(model().attributeExists("directors"))
                .andReturn();
    }

    /**
     * Метод, тестирующий создание режиссера через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном создании
     *
     * @throws Exception - любая ошибка
     */
    @Override
    @Test
    @Order(1)
    @DisplayName("Создание режиссера через MVC контроллер")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void createObject() throws Exception {
        log.info("Тест по созданию режиссера через MVC начат");
        mvc.perform(post("/directors/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .flashAttr("directorForm", directorDTO)
                                .accept(MediaType.APPLICATION_JSON)
                        // .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/directors"))
                .andExpect(redirectedUrlTemplate("/directors"))
                .andExpect(redirectedUrl("/directors"));
        log.info("Тест по созданию режиссера через MVC закончен!");
    }


    /**
     * Метод, тестирующий обновление режиссера через MVC-контроллер.
     * Авторизуемся под пользователем admin,
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном обновлении
     *
     * @throws Exception - любая ошибка
     */

    @Order(2)
    @Test
    @DisplayName("Обновление режиссера через MVC контроллер")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    //@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "andy_user")
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению режиссера через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "directors_FIO"));
        DirectorDTO foundDirectorForUpdate = directorService.searchDirectors(directorDTO.getDirectorFIO(), pageRequest).getContent().get(0);
        foundDirectorForUpdate.setDirectorFIO(directorDTOUpdated.getDirectorFIO());
        mvc.perform(post("/directors/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("directorForm", foundDirectorForUpdate)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/directors"))
                .andExpect(redirectedUrl("/directors"));
        log.info("Тест по обновлению режиссера через MVC закончен успешно");
    }


    /**
     * Метод, тестирующий удаление режиссера через MVC-контроллер.
     * Авторизуемся под пользователем admin,
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном удалении
     *
     * @throws Exception - любая ошибка
     */
    @Order(3)
    @Test
    @DisplayName("Софт удаление режиссера через MVC контроллер, тестирование 'directors/delete'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void deleteObject() throws Exception {


        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "directors_FIO"));
        DirectorDTO foundDirectorForDelete = directorService.searchDirectors(directorDTOUpdated.getDirectorFIO(), pageRequest).getContent().get(0);
        foundDirectorForDelete.setDeleted(true);
        mvc.perform(get("/directors/delete/{id}", foundDirectorForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/directors"))
                .andExpect(redirectedUrl("/directors"));

        DirectorDTO deletedDirector = directorService.getOne(foundDirectorForDelete.getId());
        assertTrue(deletedDirector.isDeleted());
        log.info("Тест по soft удалению режиссера через MVC закончен успешно!");
    }


}



