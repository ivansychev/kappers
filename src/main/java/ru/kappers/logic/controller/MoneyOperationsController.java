package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import ru.kappers.model.User;
import ru.kappers.service.UserService;

import java.math.BigDecimal;

@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping(value = "/rest/curr")
public class MoneyOperationsController {
    public static final Gson GSON = new Gson();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public User transfer (@RequestBody String content) {
        JsonObject jObject = GSON.fromJson(content, JsonElement.class).getAsJsonObject();
        User user = userService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        User kapper = userService.getByUserName(jObject.get("kapperName").getAsString());
        BigDecimal amount = jObject.get("amount").getAsBigDecimal();
        userService.transfer(user, kapper, amount);
        return user;
    }
}
