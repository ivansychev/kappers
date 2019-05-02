package ru.kappers.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.exceptions.CurrRateGettingException;
import ru.kappers.model.CurrencyRate;
import ru.kappers.service.CurrRateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

/**
 * Утилитный класс валют
 */
//todo сделать Spring Bean для данного класса и внедрять его в нужном месте вместо явного создания экземпляра
@Slf4j
public class CurrencyUtil {

    private final CurrRateService service;

    public CurrencyUtil(CurrRateService service) {
        this.service = service;
    }

    public boolean getCurrencyRatesForToday() {
        try {
            URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
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
            Date date = DateTimeUtil.parseSqlDateFromZonedDateTime(object.get("Date").getAsString());
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
            return true;
        } catch (Exception ex) {
            throw new CurrRateGettingException("Couldn't get currency rates", ex);
        }

    }

    public Date getActualCurrRateDate(Date date, String fromCurr, String toCurr, boolean currRatesGotToday) {
        boolean todaysCurrRatesGot = currRatesGotToday;
        if (service.isExist(date, fromCurr) && service.isExist(date, toCurr))
            return date;
        else {
            if (!todaysCurrRatesGot){
                todaysCurrRatesGot = getCurrencyRatesForToday();
            }
            if (todaysCurrRatesGot) {
                if (!service.isExist(date, fromCurr) || !service.isExist(date, toCurr)) {
                    LocalDate localDate = date.toLocalDate().minusDays(1);
                    date = Date.valueOf(localDate);
                    getActualCurrRateDate(date, toCurr, fromCurr, todaysCurrRatesGot);
                }
            }
        }
        return date;
    }

//TODO завести тесты на этот класс
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public BigDecimal exchange(String fromCurr, String toCurr, BigDecimal amount){
        log.debug("exchange(fromCurr: {}, toCurr: {}, amount: {})...", fromCurr, toCurr, amount);
        if (fromCurr.equals(toCurr)) {
            return amount;
        }
        Date date = Date.valueOf(LocalDate.now());
        CurrencyUtil util = new CurrencyUtil(service);
        if (!service.isExist(date, fromCurr) || !service.isExist(date, toCurr)) {
            util.getCurrencyRatesForToday();
            date = util.getActualCurrRateDate(date, fromCurr, toCurr, false);

        }
        if (fromCurr.equals("RUB")) {
            CurrencyRate rate = service.getCurrByDate(date, toCurr);
            return amount.divide(rate.getValue())
                    .multiply(BigDecimal.valueOf(rate.getNominal()));
        } else if (toCurr.equals("RUB")) {
            CurrencyRate rate = service.getCurrByDate(date, fromCurr);
            return amount.multiply(rate.getValue())
                    .multiply(BigDecimal.valueOf(rate.getNominal()));
        }
        CurrencyRate from = service.getCurrByDate(date, fromCurr);
        CurrencyRate to = service.getCurrByDate(date, toCurr);
        BigDecimal amountInRub = amount.multiply(from.getValue())
                .multiply(BigDecimal.valueOf(from.getNominal()));
        return amountInRub.divide(to.getValue())
                .multiply(BigDecimal.valueOf(to.getNominal()));
    }

}

