package ru.kappers.util;

import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DateTimeUtilTest {

	@Test
	public void getCurrentTime() throws Exception {
		final Timestamp currentTime = DateTimeUtil.getCurrentTime();
		assertNotNull(currentTime);

		TimeUnit.MILLISECONDS.sleep(5);
		assertTrue(currentTime.before(DateTimeUtil.getCurrentTime()));
	}

	@Test
	public void parseTimestampFromDate(){
		Timestamp parsed = DateTimeUtil.parseTimestampFromDate("2000-12-02+02:00");
		Timestamp expected = Timestamp.valueOf(LocalDateTime.of(2000, Month.DECEMBER, 2, 00, 0, 0));
		assertEquals(expected, parsed);
	}

	@Test(expected = RuntimeException.class)
	public void parseTimestampFromDateWithWrongFormat(){
		DateTimeUtil.parseTimestampFromDate("98798asd");
	}

	@Test
	public void parseTimestampFromZonedDateTime(){
		final ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(
				LocalDateTime.of(2001, Month.DECEMBER, 2, 10, 15, 30),
				ZoneId.of("+01:00"));
		Timestamp expectedTimestamp = Timestamp.valueOf(expectedZonedDateTime.toLocalDateTime());
		Timestamp parsedTimestamp = DateTimeUtil.parseTimestampFromZonedDateTime("2001-12-02T10:15:30+01:00");

		assertEquals(expectedTimestamp, parsedTimestamp);
	}

	@Test(expected = DateTimeParseException.class)
	public void parseTimestampFromZonedDateTimeWithWrongFormat() {
		DateTimeUtil.parseTimestampFromZonedDateTime("20011202T10:15:30+01:00");
	}
}