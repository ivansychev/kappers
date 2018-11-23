package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.service.UserService;

@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping(value = "/rest/curr")
public class MoneyOperationsController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public User transfer (@RequestBody String content) {
        Gson gson = new Gson();
        JsonObject jObject = gson.fromJson(content, JsonElement.class).getAsJsonObject();
        User user = userService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        User kapper = userService.getByUserName(jObject.get("kapperName").getAsString());
        double amount = jObject.get("amount").getAsDouble();
        userService.transfer(user, kapper, amount);
        return user;
    }
}
