package ru.kappers.model.utilmodel;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
public class FixtureFilter {
    private String paramName;
    private String value;

}
