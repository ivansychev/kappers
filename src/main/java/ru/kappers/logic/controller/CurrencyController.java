package ru.kappers.logic.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.model.CurrencyRate;
import ru.kappers.service.CurrRateService;
import ru.kappers.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

/**
 * Контроллер валют
 */
@Slf4j
@RestController
@RequestMapping(value = "/rest/admin/curr")
public class CurrencyController {
    private final CurrRateService service;

    @Autowired
    public CurrencyController(CurrRateService service) {
        this.service = service;
    }

    //TODO думаю стоит метод переименовать во что то более соответствующее реализации, например, refreshCurrencyRatesForToday. И реализацию стоит перенести в сам сервис
    //TODO кстати, данный метод собирались в будущем вызывать по расписанию, это возможно будет настроено как раз в самом методе сервиса или отдельной конфигурации Spring контекста
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public void getCurrToday() throws ParseException, MalformedURLException {
        URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            log.error("CBR daily JSON reading error", e);
            throw new RuntimeException(e);
        }
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(sb.toString());
        Date date = DateUtil.getSqlDateFromTimeStamp(object.get("Date").getAsString());
        JsonObject valutes = object.get("Valute").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entries : valutes.entrySet()) {
            JsonObject value = entries.getValue().getAsJsonObject();
            CurrencyRate rate = CurrencyRate.builder()
                    .numCode(value.get("NumCode").getAsString())
                    .charCode(value.get("CharCode").getAsString())
                    .name(value.get("Name").getAsString())
                    .date(date)
                    .nominal(value.get("Nominal").getAsInt())
                    .value(value.get("Value").getAsBigDecimal())
                    .build();
            service.save(rate);
        }
    }
}
