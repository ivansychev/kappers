package ru.kappers.service.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.CurrencyRate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Парсер курсов валют для сайта ЦБ РФ
 */
@Slf4j
@Service
public class CBRFDailyCurrencyRatesParser {
    private final JsonParser jsonParser = new JsonParser();
    @Getter
    private final URL cbrfUrl;

    /** Ссылка на JSON курсов валют ЦБ РФ по умолчанию */
    public static final String CBRF_DAILY_JSON_DEFAULT_URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    @Autowired
    public CBRFDailyCurrencyRatesParser() throws MalformedURLException {
        this(new URL(CBRF_DAILY_JSON_DEFAULT_URL));
    }

    public CBRFDailyCurrencyRatesParser(URL cbrfUrl) {
        log.debug("CBRFDailyCurrencyRatesParser(cbrfUrl: {})...", cbrfUrl);
        this.cbrfUrl = cbrfUrl;
    }

    /**
     * Парсить курсы валют с сайта ЦБ РФ
     * @return список курсов валют
     * @throws RuntimeException если во время парсинга произошла ошибка
     */
    public List<CurrencyRate> parseFromCBRF() {
        log.debug("parseFromCBRF()...");
        return parseFromURL(cbrfUrl);
    }

    /**
     * Парсить курсы валют с сайта
     * @param url ссылка на сайт
     * @return список курсов валют
     * @throws RuntimeException если во время парсинга произошла ошибка
     */
    public List<CurrencyRate> parseFromURL(URL url) {
        log.debug("parseFromURL(url: {})...", url);
        final String json = getJSONStringFromURL(url);
        return parseFromJSON(json);
    }

    protected String getJSONStringFromURL(URL url) {
        log.debug("getJSONStringFromURL(url: {})...", url);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            String msg = "CBR daily JSON reading error";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
        return sb.toString();
    }

    /**
     * Парсить курсы валют из строки JSON-а
     * @param json строка с JSON
     * @return список курсов валют
     */
    public List<CurrencyRate> parseFromJSON(String json) {
        log.debug("parseFromJSON(json: {})...", json);
        JsonObject object = (JsonObject) jsonParser.parse(json);
        LocalDate date = ZonedDateTime.parse(object.get("Date").getAsString(), DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .toLocalDate();
        JsonObject valutes = object.get("Valute").getAsJsonObject();
        if (valutes.size() == 0) {
            return Collections.emptyList();
        }
        List<CurrencyRate> currencyRates = new ArrayList<>(valutes.size());
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
            currencyRates.add(rate);
        }
        return currencyRates;
    }
}
