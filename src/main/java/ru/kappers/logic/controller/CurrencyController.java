package ru.kappers.logic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.service.CurrRateService;
import ru.kappers.util.CurrencyUtil;

import java.net.MalformedURLException;
import java.text.ParseException;

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
        CurrencyUtil currencyUtil = new CurrencyUtil(service);
        currencyUtil.getCurrencyRatesForToday();
    }
}
