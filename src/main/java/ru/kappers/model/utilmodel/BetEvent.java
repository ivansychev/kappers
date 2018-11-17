package ru.kappers.model.utilmodel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.service.FixtureService;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by Shoma on 29.09.2018.
 */
@Data
@Log4j
@Service
public class BetEvent {
    public JsonObject getEvent(Fixture fixture) {
        Odds odds = new Odds(fixture);
        Gson gson = new Gson();
        String s = gson.toJson(odds);
        return new JsonObject().getAsJsonObject(s);
    }
}
