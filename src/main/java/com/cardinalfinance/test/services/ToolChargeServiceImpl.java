package com.cardinalfinance.test.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinalfinance.test.data.Tool;
import com.cardinalfinance.test.data.ToolCharge;
import com.cardinalfinance.test.data.ToolChargesData;
import com.cardinalfinance.test.data.ToolsData;
import com.cardinalfinance.test.utilities.DataObjectService;
import com.cardinalfinance.test.utilities.DateUtilities;

/*
 * Service to do all the calculations needed
 * for the tool charges
 */
@Service
public class ToolChargeServiceImpl implements ToolChargeService{
	@Autowired
	private DataObjectService dataService;
	
	@Autowired
	private DateUtilities dtUtil;

	@Override
	public StringBuilder getCalculatedResult(int noOfDays, LocalDate startDate, String toolCode, float discount) {
		
		Tool tool = getToolInfo(toolCode);
		ToolCharge tCharge = getToolChargeInfo(tool.getToolType());
		List<LocalDate> lstDates = dtUtil.getDateList(startDate, noOfDays);
		int noDaysToCharge = getNoOfDays(lstDates,noOfDays,tCharge);
		double chargeBeforeDiscount = noDaysToCharge * tCharge.getDailyCharge();
		double chargeAfterDiscount;
		if (discount > 0) {
			 double discountAmount = chargeBeforeDiscount * (discount/100);
		     chargeAfterDiscount = chargeBeforeDiscount - discountAmount;
	     } else {
	    	 chargeAfterDiscount = chargeBeforeDiscount;
	     }
		return formulateResult(tool,tCharge,lstDates.get(noOfDays -1),startDate,chargeBeforeDiscount,
				               chargeAfterDiscount,discount,noOfDays,noDaysToCharge);
	}
	
	
	private StringBuilder formulateResult(Tool tool, ToolCharge tCharge, LocalDate dueDate, LocalDate startDate, 
			                              double beforeDiscount, double afterDiscount, float discount, int noOfDays,
			                              int chargeDays) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		DecimalFormat df = new DecimalFormat("#.##"); 
		StringBuilder sb = new StringBuilder();
		sb.append("Tool code - " + tool.getToolCode() + " \n");
		sb.append("Tool type - " + tool.getToolType()+ " \n");
		sb.append("Tool brand - " + tool.getBrand()+ " \n");
		sb.append("Rental days  - " + noOfDays+ " \n");
		sb.append("Check out date  - " + startDate.format(dateFormatter)+ " \n");
		sb.append("Due date -  " + dueDate.format(dateFormatter)+ " \n");
		sb.append("Daily rental charge - " + currencyFormat.format(Double.valueOf(df.format(tCharge.getDailyCharge()))) + " \n");
		sb.append("Charge days -" + chargeDays + " \n");
		sb.append("Pre-discount charge - " + currencyFormat.format(Double.valueOf(df.format(beforeDiscount))) + " \n");
		sb.append("Discount percent - " + (int)discount + "% \n");
		sb.append("Discount amount - " + currencyFormat.format(Double.valueOf(df.format(beforeDiscount - afterDiscount))) + " \n");
		sb.append("Final charge - " + currencyFormat.format(Double.valueOf(df.format(afterDiscount))) + " \n");
		return sb;
	}
			                  
	//get the individual tool info based on toolcode
    private Tool getToolInfo(String toolCode) {
    	ToolsData tData = dataService.getTools();
    	Tool tool = tData.getTools().getTool().stream()
    			   .filter(s -> s.getToolCode().equalsIgnoreCase(toolCode.trim()))
    			   .findFirst().get();
    	return tool;
    }
	
    
    private ToolCharge getToolChargeInfo(String toolType) {
    	ToolChargesData tcData = dataService.getCharges();
    	ToolCharge tc = tcData.getToolcharges().stream()
    			        .filter(s -> s.getTooltype().equalsIgnoreCase(toolType.trim()))
    			        .findFirst()
    			        .orElse(null)
    			        ;
    	
    	return tc;
    }
    
    
    private int getNoOfDays(List<LocalDate> lstDates, int dayCount, ToolCharge tCharge) {
    	
    	int noOfDays = lstDates.size();
    	//check first if they don't charge on Holiday for this tool
    	if (!tCharge.isHolidayCharge()) {
    		noOfDays += checkLaborDay(lstDates);
    		noOfDays += checkJulyFourth(lstDates);
    	}
    	if (!tCharge.isWeekendCharge()) {
    		noOfDays += checkWeekendDays(lstDates);
    	}
    	
    	return noOfDays;
    }
    
    // Function to return 0 or -1 to account for labor day
    // if tool is not going to be charged for holiday
    private int checkLaborDay(List<LocalDate> lstInDates) {
    	boolean isLaborPresent = lstInDates.stream()
    			.filter(s -> s.isEqual(dtUtil.getLaborDay(s.getYear())))
    			.findAny().isPresent();
    	
    	if (isLaborPresent) {
    		return -1;
    	} else {
    		return 0;
    	}
    }
    
    //Return -1 if there is July 4th falling within the ranges of dates
    private int checkJulyFourth(List<LocalDate> lstInDates) {
    	boolean isJulyFourth =  lstInDates.stream()
    			.filter(s -> (dtUtil.isJulyFourth(s)))
    			.findAny().isPresent();
    	if (isJulyFourth) {
    		return -1;
    	} else {
    		return 0;
    	}
    }
    
    private int checkWeekendDays(List<LocalDate> lstInDates) {
    	int noWeekendDays = (int)lstInDates.stream()
    			            .filter(dtUtil::isWeekend)
    			            .count();
    	if (noWeekendDays > 0) {
    		noWeekendDays *= -1;
    	}
    	
    	return noWeekendDays;
    	
    }

}
