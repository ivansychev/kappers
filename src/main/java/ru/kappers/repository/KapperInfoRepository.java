package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.Stat;
import ru.kappers.model.User;

public interface KapperInfoRepository extends CrudRepository<KapperInfo, Integer> {
	KapperInfo getKapperInfoByUser(User user);
	void deleteByUser(User user);
}
