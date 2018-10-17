package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.PersonalInfo;

public interface PersonalInfoRepository extends CrudRepository<PersonalInfo, Integer>
{
}
