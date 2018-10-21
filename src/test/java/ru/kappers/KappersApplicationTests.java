package ru.kappers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.config.AdditionalBDConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KappersApplication.class, AdditionalBDConfig.class })
public class KappersApplicationTests {

	@Test
	public void contextLoads() {
	}

}
