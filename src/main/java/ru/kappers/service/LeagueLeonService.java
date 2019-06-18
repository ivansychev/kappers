package ru.kappers.service;

import ru.kappers.model.leonmodels.LeagueLeon;

public interface LeagueLeonService {
    LeagueLeon getByName(String name);
    LeagueLeon save(LeagueLeon league);
}
