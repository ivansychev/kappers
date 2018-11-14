package ru.kappers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import ru.kappers.service.HistoryServiceImplTest;
import ru.kappers.service.KapperInfoServiceImplTest;
import ru.kappers.service.UserServiceImplTest;
import ru.kappers.util.DateUtilTest;
import ru.kappers.service.RoleServiceImplTest;

@RunWith(Suite.class)
@SpringBootTest(classes = { KappersApplication.class})
@ActiveProfiles("test")
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@Suite.SuiteClasses({
		RoleServiceImplTest.class,
		DateUtilTest.class,
		UserServiceImplTest.class,
		KapperInfoServiceImplTest.class,
		HistoryServiceImplTest.class
})
public class KappersApplicationTests {

	@Test
	public void contextLoads() {
	}

}
