package ru.kappers.logic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import ru.kappers.User;
import ru.kappers.UsersRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private static List<User> persons = new ArrayList<>();
    private final UsersRepository repository;
  //  User user;
    public MainController(UsersRepository repository) {
        this.repository = repository;
//        user = User.builder().name("Mazi").password("Sersas").build();
//        repository.save(user);
    }

    @RequestMapping(value = "/mazi", method = RequestMethod.GET)
    public String personList(Model model) {
        persons = repository.findAll();
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
