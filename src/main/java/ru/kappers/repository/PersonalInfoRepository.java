package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.PersonalInfo;
import ru.kappers.model.User;

public interface PersonalInfoRepository extends CrudRepository<PersonalInfo, Integer>
{
    void deleteAllByUser(User user);
    PersonalInfo getByUser(User user);
}
