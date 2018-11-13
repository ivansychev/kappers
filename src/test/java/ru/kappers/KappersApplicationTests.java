package ru.kappers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.service.HistoryServiceImplTest;
import ru.kappers.service.KapperInfoServiceImplTest;
import ru.kappers.service.UserServiceImplTest;
import ru.kappers.service.impl.KapperInfoServiceImpl;
import ru.kappers.util.DateUtilTest;
import ru.kappers.util.RoleUtilTest;

@RunWith(Suite.class)
@SpringBootTest(classes = { KappersApplication.class})
@ActiveProfiles("test")
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@Suite.SuiteClasses({
		RoleUtilTest.class,
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
