package ru.kappers.util;

import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Log4j
public class DateUtil {
	private static DateFormat df=new SimpleDateFormat("yyyyMMdd");
	
	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Timestamp convertDate(String date) {
		try {
			Date ddate=df.parse(date);
			return new Timestamp(ddate.getTime());
		} catch (ParseException e) {
			log.error("Wrong date given");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}
