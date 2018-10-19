package ru.kappers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import ru.kappers.repository.KapperInfoRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.KapperInfoService;

@Service
public class KapperInfoServiceImpl implements KapperInfoService {
	private final UsersRepository usersRepository;
	private final KapperInfoRepository kapperRepository;

	@Autowired
	public KapperInfoServiceImpl(UsersRepository usersRepository, KapperInfoRepository kapperRepository) {
		this.usersRepository = usersRepository;
		this.kapperRepository = kapperRepository;
	}

	@Override
	public KapperInfo initKapper(User user, KapperInfo kapperInfo) {
		return null;
	}

	@Override
	public void delete(User user) {

	}

	@Override
	public KapperInfo getByUser(User user) {
		return null;
	}

	@Override
	public KapperInfo editBank(KapperInfo kapperInfo) {
		return null;
	}
}
