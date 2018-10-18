package ru.kappers.logic.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import ru.kappers.model.Roles;
import ru.kappers.model.User;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Controller
public class MainController {
    private static List<User> persons = new ArrayList<>();
    private final UserService service;

    public MainController(UserService service) {
        this.service = service;
//        user = User.builder().name("Mazi").password("Sersas").build();
//        repository.save(user);
    }

    @RequestMapping(value = "/mazi", method = RequestMethod.GET)
    public String personList(Model model) {
//        User user = User.builder()
//                .name("Shama2")
//                .password("Serasdsas")
//                .roleId(Roles.RoleType.ROLE_USER.getId())
//                .userName("shama123")
//                .currency("RUB")
//                .lang("RUSSIAN")
//                .email("sas@sasas")
//                .dateOfBirth(Timestamp.valueOf(LocalDateTime.of(1985, Month.APRIL, 29, 1,45)))
//                .dateOfRegistration(new Timestamp(System.currentTimeMillis()))
//                .isblocked(false).build();
//        service.addUser(user);
        persons = service.getAll();
        model.addAttribute("persons", persons);
        return "index1";
    }

    @RequestMapping(value = "/kapper")
    public ModelAndView kapper(@ModelAttribute("userJSP") String s) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("kapper");
        modelAndView.addObject("userJSP", "Это страница каппера");
        return modelAndView;
    }

    @RequestMapping(value = "/userpage")
    public ModelAndView userpage(@ModelAttribute("userJSP") String s) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userpage");
        modelAndView.addObject("userJSP", "Это страница пользователя");
        return modelAndView;
    }

    @RequestMapping(value = "/admin")
    public ModelAndView admin(@ModelAttribute("userJSP") String s) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userJSP", "Это страница администратора");
        return modelAndView;
    }

    @RequestMapping(value = "/about")
    public ModelAndView about(@ModelAttribute("userJSP") String s) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userJSP", "О проекте");
        return modelAndView;
    }

    @RequestMapping(value = "/contacts")
    public ModelAndView contacts(@ModelAttribute("userJSP") String s) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userJSP", "Контактная информация");
        return modelAndView;
    }

    @RequestMapping(value = "/privatepage")
    public ModelAndView privatepage(@ModelAttribute("userJSP") String s) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userJSP", "Личный кабинет");
        return modelAndView;
    }
}
