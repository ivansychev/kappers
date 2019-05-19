package ru.kappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kappers.config.KappersConfig;

@Slf4j
@SpringBootApplication
@Import({KappersConfig.class})
public class KappersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappersApplication.class, args);
	}
}
