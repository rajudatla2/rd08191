package com.cardinalfinance.test.services;

import java.time.LocalDate;

public interface ToolChargeService {
	String JULY_HOLIDAY = "07/04";
	
	public StringBuilder getCalculatedResult(int noOfDays, LocalDate startDate, String toolCode, float discount);
	

}
