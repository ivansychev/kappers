package ru.kappers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import ru.kappers.service.*;
import ru.kappers.util.DateUtilTest;

@RunWith(Suite.class)
@SpringBootTest(classes = { KappersApplication.class})
@ActiveProfiles("test")
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@Suite.SuiteClasses({
		RoleServiceImplTest.class,
		DateUtilTest.class,
		UserServiceImplTest.class,
		KapperInfoServiceImplTest.class,
		HistoryServiceImplTest.class,
		FixtureServiceImplTest.class
})
public class KappersApplicationTests {

	@Test
	public void contextLoads() {
	}

}
