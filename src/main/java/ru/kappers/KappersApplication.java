package ru.kappers;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Log4j
@SpringBootApplication
public class KappersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappersApplication.class, args);
	}
}
