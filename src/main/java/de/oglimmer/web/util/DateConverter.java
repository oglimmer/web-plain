package de.oglimmer.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

	public static boolean isValid(String dateString) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			df.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String toString(Date date) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(date);
	}

	public static java.sql.Date toSqlDate(String dateString) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			return new java.sql.Date(df.parse(dateString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
