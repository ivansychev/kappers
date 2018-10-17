package ru.kappers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.User;
import ru.kappers.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersController {
//private final UsersRepository repository;
//
//    public UsersController(UsersRepository repository) {
//        this.repository = repository;
//        User user = User.builder().name("Mazi").password("Sersas").build();
//        repository.save(user);
//    }
//
//    @GetMapping("/noopen")
//    public List<User> allUsers() {
//
//        return repository.findAll();
//    }

}
