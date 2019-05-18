package ru.kappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kappers.config.AppConfig;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@Import({AppConfig.class})
public class KappersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappersApplication.class, args);
	}
}
