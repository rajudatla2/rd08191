package com.cardinalfinance.test.utilities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/*
 * Utility class to consolidate all the date
 * methods needed for the tool charges
 */
@Component
public class DateUtilities {
	
	//Get the Labor day date given the year as full int i.e. 2024
	public  LocalDate getLaborDay(int year) {
        LocalDate septemberFirst = getSeptemberFirst(year);
        return getFirstMondayAfter(septemberFirst);
    }

    private  LocalDate getSeptemberFirst(int year) {
        return LocalDate.of(year, Month.SEPTEMBER, 1);
    }

    private  LocalDate getFirstMondayAfter(LocalDate date) {
        while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.plusDays(1);
        }
        return date;
    }
    
    
    //Check if the given date is a weekend
    public boolean isWeekend(LocalDate inDate) {
    	DayOfWeek dayOfWeek = getDayOfWeek(inDate);
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public  LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    /*private boolean isWeekendDate(LocalDate date) {
        DayOfWeek dayOfWeek = getDayOfWeek(date);
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }*/

    public DayOfWeek getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek();
    }
    
    //Check if this is July 4th for given date 
    public boolean isJulyFourth(LocalDate date) {
        return date.getMonthValue() == 7 && date.getDayOfMonth() == 4;
    }
    
    //get the non weekend date when july 4th falls on weekend
    public LocalDate adjustForWeekend(LocalDate inDate) {
        DayOfWeek dayOfWeek = inDate.getDayOfWeek();
        
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return inDate.minusDays(1);  // July 3rd
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return inDate.plusDays(1);   // July 5th
        }
        
        return inDate;  // Original date if it's not on a weekend
    }

    
    //Get list of Dates given a date and No of Days to parse through
    public List<LocalDate> getDateList(LocalDate inDt, int noOfDays){
    	List<LocalDate> lstOfDates = new ArrayList<LocalDate>();
    	lstOfDates.add(inDt);
    	for(int i =1;  i <= (noOfDays - 1); i++) {
    		 LocalDate incDate = inDt.plusDays(i);
    		 lstOfDates.add(incDate);
    		
    	}
    	return lstOfDates;
    }

}
