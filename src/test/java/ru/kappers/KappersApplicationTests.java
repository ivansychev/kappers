package ru.kappers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import ru.kappers.logic.controller.CurrencyControllerTest;
import ru.kappers.logic.controller.web.EventControllerTest;
import ru.kappers.service.*;
import ru.kappers.service.impl.ApplicationStartListenerTest;
import ru.kappers.util.DateTimeUtilTest;

@RunWith(Suite.class)
@SpringBootTest(classes = { KappersApplication.class})
@ActiveProfiles("test")
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@Suite.SuiteClasses({
		RoleServiceImplTest.class,
		DateTimeUtilTest.class,
		UserServiceImplTest.class,
		KapperInfoServiceImplTest.class,
		HistoryServiceImplTest.class,
		FixtureServiceImplTest.class,
        CurrRateServiceImplTest.class,
		EventControllerTest.class,
		CurrencyControllerTest.class,
		ApplicationStartListenerTest.class
})
public class KappersApplicationTests {

	@Test
	public void contextLoads() {
	}

}
