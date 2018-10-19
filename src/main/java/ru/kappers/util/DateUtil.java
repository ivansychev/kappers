package ru.kappers.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			System.out.println("WRONG DATA GIVEN");
			e.printStackTrace();
			return null;
		}
		
	}
}
