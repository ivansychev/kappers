package ru.kappers.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.exceptions.CurrRateGettingException;
import ru.kappers.model.CurrencyRate;
import ru.kappers.service.CurrRateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

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
}

