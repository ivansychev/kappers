package ru.kappers.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class DateUtilTest {

	@Test
	public void convertDate() throws ParseException {
		String incomeDate = "20001202";
		Timestamp t1 = DateUtil.convertDate(incomeDate);
		t1.setHours(12);
		t1.setMinutes(0);
		Timestamp t2 = Timestamp.valueOf(LocalDateTime.of(2000, Month.DECEMBER, 2, 12, 0));
		Assert.assertEquals(t1, t2);
	}

	@Test(expected = ParseException.class)
	public void convertDateWrong() throws ParseException {
		String incomeDate = "98798asd";
		Timestamp t1 = DateUtil.convertDate(incomeDate);
	}
}