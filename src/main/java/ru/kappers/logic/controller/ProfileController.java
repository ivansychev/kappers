package ru.kappers.logic.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kappers.model.User;

@Controller
@RequestMapping(value = "/rest/profile")
public class ProfileController {
    /**
     * Пример REST взаимодействия
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserInfoExampleRest(@PathVariable Long id) {
        // return userRepository.findById(id);
        return User.builder()
                .currency("Доллар")
                .lang("RU")
                .name("Вася")
                .password("vasya96")
                .roleId(1)
                .userName("vasya")
                .email("vasya@gmail.com")
                .isblocked(false)
                .userId(id.intValue())
                .build();
    }
}
