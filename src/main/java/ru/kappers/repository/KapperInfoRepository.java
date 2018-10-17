package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.Stat;

public interface KapperInfoRepository extends CrudRepository<KapperInfo, Integer> {
}
