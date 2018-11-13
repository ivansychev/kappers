package ru.kappers.util;

import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Log4j
public class DateUtil {

	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Получить Timestamp из строки
	 * @param date строка в формате {@link DateTimeFormatter#BASIC_ISO_DATE}
	 * @return новый Timestamp
	 */
	public static Timestamp convertDate(String date) {
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
		return Timestamp.valueOf(localDate.atStartOfDay());
	}
}
