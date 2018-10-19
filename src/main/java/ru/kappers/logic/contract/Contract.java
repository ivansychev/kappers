package ru.kappers.logic.contract;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import ru.kappers.repository.KapperInfoRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;

import java.util.Map;

/**
 * Created by Shoma on 29.09.2018.
 */

@Log4j
public class Contract implements IContract {

	private UserService userService;
	private KapperInfoService kapperService;

	@Autowired
	public Contract(UserService userService, KapperInfoService kapperService) {
		this.userService = userService;
		this.kapperService = kapperService;
	}

	@Override
	public KapperInfo initCapper(User user) {
		return null;
	}

	@Override
	public KapperInfo getKapperInfo(User user) {
		return null;
	}

	@Override
	public void blockTokens(User user, double amount) {

	}

	@Override
	public Map<User, KapperInfo> getAllInfo() {
		return null;
	}

	@Override
	public void unblockAmount(User user, double amount) {

	}

	@Override
	public void withdrawBlockedTokens(User user, double amount) {

	}
}
