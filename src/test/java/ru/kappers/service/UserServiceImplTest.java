package ru.kappers.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.Roles;
import ru.kappers.model.User;
import ru.kappers.util.DateUtil;

import java.util.List;

import static org.junit.Assert.*;
@ComponentScan("ru.kappers")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {
	@Autowired
	private static UserService userService;
	private static User admin;
	private static User user;
	private static User kapper;

	static {
		admin = User.builder()
				.userName("admin")
				.name("админ")
				.password("asasdgfas")
				.dateOfBirth(DateUtil.convertDate("19650806"))
				.currency("RUB")
				.lang("RUSSIAN")
				.roleId(Roles.RoleType.ROLE_ADMIN.getId())
				.build();
		user = User.builder()
				.userName("user")
				.name("юзер")
				.password("assaasas")
				.dateOfBirth(DateUtil.convertDate("19650806"))
				.currency("RUB")
				.lang("RUSSIAN")
				.roleId(Roles.RoleType.ROLE_USER.getId())
				.build();
		kapper = User.builder()
				.userName("kapper")
				.name("каппер")
				.password("assaasas")
				.dateOfBirth(DateUtil.convertDate("19650806"))
				.currency("RUB")
				.lang("RUSSIAN")
				.roleId(Roles.RoleType.ROLE_KAPPER.getId())
				.build();
	}

	@BeforeClass
	public static void addUsers() {
		User user1 = userService.addUser(admin);
		User user2 = userService.addUser(user);
		User user3 = userService.addUser(kapper);
//		assertEquals(user1, admin);
//		assertEquals(user2, user);
//		assertEquals(user3, kapper);
	}

	@AfterClass
	public static void deleteUsers() {
		userService.delete(user);
		userService.delete(admin);
		userService.delete(kapper);
	}

	@Test
	public void delete() {
		User beforeDelete = user;
		userService.delete(user);
		User afterDelete = userService.getById(beforeDelete.getUserId());
		assertNull(afterDelete);
		userService.addUser(beforeDelete);
		afterDelete = userService.getById(beforeDelete.getUserId());
		assertNotNull(afterDelete);
	}

	@Test
	public void getByUserName() {
		User user1 = userService.getByUserName("kapper");
		assertNotNull(user1);
		assertEquals(user1, kapper);
		assertNotEquals(user1, user);
		assertNotEquals(user1, admin);
	}

	@Test
	public void getByName() {
		User user1 = userService.getByName("админ");
		assertNotNull(user1);
		assertNotEquals(user1, kapper);
		assertNotEquals(user1, user);
		assertEquals(user1, admin);
	}

	@Test
	public void getById() {
		User user1 = userService.getById(user.getUserId());
		assertNotNull(user1);
		assertNotEquals(user1, kapper);
		assertEquals(user1, user);
		assertNotEquals(user1, admin);
	}

	@Test
	public void editUser() {
		User user1 = userService.getByUserName("admin");
		String curr = user1.getCurrency();
		assertNotNull(user1);
		user1.setCurrency("USD");
		userService.editUser(user1);
		user1 = userService.getByUserName("admin");
		assertNotEquals(curr, user1.getCurrency());
		assertEquals(curr, "USD");
	}

	@Test
	public void getAll() {
		List<User> all = userService.getAll();
		assertTrue(all.contains(user));
		assertTrue(all.contains(admin));
		assertTrue(all.contains(kapper));

	}

	@Test
	public void getAllByRole() {
		List<User> admins = userService.getAllByRole("ROLE_ADMIN");
		assertNotNull(admins);
		assertNotEquals(0, admins.size());
		assertTrue(admins.contains(admin));
	}

	@Test
	public void hasRole() {
		assertTrue(userService.hasRole(kapper, "ROLE_KAPPER"));
		assertFalse(userService.hasRole(user, "ROLE_KAPPER"));
		assertFalse(userService.hasRole(kapper, "ROLE_ADMIN"));
		assertTrue(userService.hasRole(kapper, Roles.RoleType.ROLE_KAPPER.getId()));
	}

	@Test
	public void getRole() {
		Roles role = userService.getRole(admin);
		assertEquals("ROLE_ADMIN", role.getRoleName().name());
	}

	@Test
	public void getHistory() {
		//TODO
	}

	@Test
	public void getStat() {
		//TODO
	}

	@Test
	public void getKapperInfo() {
		//TODO
	}

	@Test
	public void getInfo() {
		//TODO
	}
}