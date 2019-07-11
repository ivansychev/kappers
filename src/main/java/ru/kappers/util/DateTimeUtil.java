package ru.kappers.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Утилитный класс даты и времени
 */
@Slf4j
public final class DateTimeUtil {

	/** миллисекунд в часе */
	public static final int MILLISECONDS_IN_HOUR = 3600 * 1000;
	/** миллисекунд в сутках */
	public static final int MILLISECONDS_IN_DAY = 24 * MILLISECONDS_IN_HOUR;
	/** миллисекунд в неделе */
	public static final int MILLISECONDS_IN_WEEK = MILLISECONDS_IN_DAY * 7;

	/**
	 * Получить экземпляр {@link LocalDateTime} на начало дня из строки
	 * @param date строка с датой в формате {@link DateTimeFormatter#ISO_OFFSET_DATE}
	 * @return экземпляр {@link LocalDateTime}
	 */
	public static LocalDateTime parseLocalDateTimeFromStartOfDate(String date) {
		log.debug("parseLocalDateTimeFromStartOfDate(date: {})...", date);
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_OFFSET_DATE);
		final LocalDateTime result = localDate.atStartOfDay();
		log.debug("parseLocalDateTimeFromStartOfDate(date: {}) return result: {}", date, result);
		return result;
	}

	/**
	 * Получить экземпляр {@link LocalDateTime} из строки
	 * @param dateTime строка с датой и временем в формате {@link DateTimeFormatter#ISO_ZONED_DATE_TIME}
	 * @return экземпляр {@link LocalDateTime}
	 */
	public static LocalDateTime parseLocalDateTimeFromZonedDateTime(String dateTime) {
		log.debug("parseLocalDateTimeFromZonedDateTime(dateTime: {})...", dateTime);
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
		final LocalDateTime result = zonedDateTime.toLocalDateTime();
		log.debug("parseLocalDateTimeFromZonedDateTime(dateTime: {}) return result: {}", dateTime, result);
		return result;
	}

	/**
	 * Получить экземпляр {@link Timestamp} из строки
	 * @param date строка с датой в формате {@link DateTimeFormatter#ISO_OFFSET_DATE}
	 * @return экземпляр {@link Timestamp}
	 */
	public static Timestamp parseTimestampFromDate(String date) {
		log.debug("parseTimestampFromDate(date: {})...", date);
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_OFFSET_DATE);
		final Timestamp result = Timestamp.valueOf(localDate.atStartOfDay());
		log.debug("parseTimestampFromDate(date: {}) return result: {}", date, result);
		return result;
	}

}