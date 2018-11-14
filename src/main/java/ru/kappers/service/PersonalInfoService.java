package ru.kappers.service;

import ru.kappers.model.PersonalInfo;
import ru.kappers.model.User;

public interface PersonalInfoService {
    PersonalInfo addInfo(PersonalInfo info);

    void clearPersonalInfo(User user);

    PersonalInfo getByUser(User user);

    PersonalInfo editInfo(PersonalInfo info);
}
