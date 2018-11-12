package ru.kappers.logic.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.service.FixtureService;
import ru.kappers.service.UserService;
import ru.kappers.util.JsonUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j
@Controller
public class MainController {
    private static List<User> persons = new ArrayList<>();
    private final UserService service;
    private FixtureService fService;

    public MainController(UserService service, FixtureService fService) {
        this.service = service;
        this.fService = fService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String personList(Model model) throws UnirestException {
//        User user = User.builder()
//                .name("kapper")
//                .password("kapper")
//                .roleId(3)
//                .userName("kapper")
//                .currency("RUB")
//                .lang("RUSSIAN")
//                .email("kapper@kapper")
//                .dateOfBirth(Timestamp.valueOf(LocalDateTime.of(1987, Month.APRIL, 18, 1, 45)))
//                .dateOfRegistration(new Timestamp(System.currentTimeMillis()))
//                .isblocked(false).build();
//        service.addUser(user);

      //  JSONObject jsonObject = JsonUtil.loadLiveFixtures();
//        Map<Integer, Fixture> fixturesFromJson = JsonUtil.getFixturesFromJson(jsonObject.toString());
//        for (Map.Entry<Integer, Fixture> entry:fixturesFromJson.entrySet()) {
//            fService.addRecord(entry.getValue());
//        }
        JSONObject jsonObject2 = JsonUtil.loadFixturesByDate(LocalDate.of(2018,11,13));

      Map<Integer, Fixture>  fixturesFromJson = JsonUtil.getFixturesFromJson(jsonObject2.toString());
        for (Map.Entry<Integer, Fixture> entry:fixturesFromJson.entrySet()) {
            fService.addRecord(entry.getValue());
        }
        persons = service.getAll();
        model.addAttribute("persons", persons);
        return "test";
    }

    @RequestMapping(value = "/fixturesAll", method = RequestMethod.GET)
    public String fixturesList(Model model) throws UnirestException {
        List<Fixture> fixtures;
        fixtures = fService.getAll();
        model.addAttribute("fixtures", fixtures);
        return "fixtures";
    }
    @RequestMapping(value = "/fixturesFinished", method = RequestMethod.GET)
    public String fixturesListFinished(Model model) throws UnirestException {
        List<Fixture> fixtures;
        fixtures = fService.getAll().stream().filter(s->s.getStatus().equals("Match Finished")).collect(Collectors.toList());
        model.addAttribute("fixtures", fixtures);
        return "fixtures";
    }
    @RequestMapping(value = "/fixturesAvailable", method = RequestMethod.GET)
    public String fixturesListAvailable(Model model) throws UnirestException {
        List<Fixture> fixtures;
        fixtures = fService.getAll().stream().filter(s->s.getStatus().equals("Not Started")).collect(Collectors.toList());
        model.addAttribute("fixtures", fixtures);
        return "fixtures";
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
