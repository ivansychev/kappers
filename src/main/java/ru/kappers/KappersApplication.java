package ru.kappers;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kappers.config.AppConfig;

@Log4j
@SpringBootApplication
@Import({AppConfig.class})
public class KappersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappersApplication.class, args);
	}
}
