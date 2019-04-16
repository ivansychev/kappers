package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.model.CurrencyRate;
import ru.kappers.service.CurrRateService;
import ru.kappers.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/rest/admin/curr")
public class CurrencyController {
    @Autowired
    private CurrRateService service;

    @ResponseBody
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public void getCurrToday() throws ParseException {
        URL url;
        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
            stream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
