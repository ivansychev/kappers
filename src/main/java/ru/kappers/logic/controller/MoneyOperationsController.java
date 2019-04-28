package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping(value = "/rest/curr")
public class MoneyOperationsController {
    public static final Gson GSON = new Gson();

    @Autowired
    private UserService userService;

    /**
     * Метод transfer предназначен для перевода денег от Юзера к Капперу. Осуществляться должен в момент покупки евента.
     * JSON, который клиент отправляет, выглядит следующим образом
     * {
     * "kapper":"kapper1",
     * "amount":"1"
     * }
     * <p>
     * Здесь указывается userName каппера и переводимая сумма. В качестве отправителя получаем User от имени которого совершена авторизация
     */

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String transfer(@RequestBody String content) {
        JsonObject jObject = GSON.fromJson(content, JsonElement.class).getAsJsonObject();
        User user = userService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        User kapper = userService.getByUserName(jObject.get("kapper").getAsString());
        BigDecimal amount = jObject.get("amount").getAsBigDecimal();
        try {
            userService.transfer(user, kapper, amount);
        } catch (Exception e) {
            log.error("Couldn't transfer money from {} to {}. Exception is {}", user.getUserName(), kapper.getUserName(), e.getMessage());
            return GSON.toJson(e);
        }
        return null; //TODO нужно сделать фабрику ответов. Отправлять ответ со статусом
    }
}
