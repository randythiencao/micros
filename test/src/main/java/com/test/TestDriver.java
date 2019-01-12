package com.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDriver {
	public static void main(String[] args) {
		String date = "02/13/2012";
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		convertedDate = convertedDate.withDayOfMonth(
		                                convertedDate.getMonth().length(convertedDate.isLeapYear()));
		convertedDate.getDayOfMonth();
	}
}
