package com.cardinalfinance.test.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardinalfinance.test.services.ToolChargeServiceImpl;
import com.cardinalfinance.test.utilities.DateUtilities;
/*
 * Sample controller to test rest service for tool charges
 */
@RestController
public class ToolChargesController {
	@Autowired
	private DateUtilities dtUtility;
	
	@Autowired
	private ToolChargeServiceImpl toolChargesServ;
	
	
	//Sample get url localhost:8080/getcharges?code=JAKR&chkDate=09/03/2015&noDays=5&discount=101
	@GetMapping("/getcharges")
	public StringBuilder getChargesResult(@RequestParam(name = "code") String toolCode, @RequestParam(name = "chkDate") String startDate,
			                             @RequestParam(name = "noDays") int rentDays, @RequestParam(name = "discount") String discount) {
		
		    LocalDate checkoutDate = dtUtility.parseDate(startDate);
		    float discountPercent = Float.valueOf(discount.replace("%", ""));
		    StringBuilder retSb = toolChargesServ.getCalculatedResult(rentDays, checkoutDate, toolCode, discountPercent);
		    return retSb;
		
	}

}
