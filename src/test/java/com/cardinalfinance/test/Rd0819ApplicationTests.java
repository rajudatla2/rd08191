package com.cardinalfinance.test;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.cardinalfinance.test.data.ToolChargesData;
import com.cardinalfinance.test.data.ToolsData;
import com.cardinalfinance.test.repo.ToolsDataSource;
import com.cardinalfinance.test.services.ToolChargeServiceImpl;
import com.cardinalfinance.test.utilities.DataObjectService;
import com.cardinalfinance.test.utilities.DateUtilities;

@SpringBootTest
class Rd0819ApplicationTests {
	@Autowired
	private ToolsDataSource tDataSrc;
	
	@Autowired
	private DataObjectService dObjService;
	
	@Autowired
	private ToolChargeServiceImpl toolCharges;
	
	@Autowired
	DateUtilities dateUtil; 
	
	

	@Test
	void contextLoads() {
	}
	
	@Test
	void getToolsDataTest() {
		ToolsData tData = (ToolsData)tDataSrc.getData("tools");
		
		assertNotNull(tData.getTools());
	}
	
	@Test
	void getChargesDataTest() {
		ToolChargesData tChargesData = (ToolChargesData) tDataSrc.getData("charges");
		assertNotNull(tChargesData.getToolcharges());
	}
	
	@Test
	void getDataServiceTest() {
		ToolsData tData = dObjService.getTools();
		assertNotNull(tData.getTools());
	}
	
	@Test
	void getLaborDayTest() {
		LocalDate dt = dateUtil.getLaborDay(2015);
		//DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate returnDt =  LocalDate.parse("2015-09-07");
		int dtDiff = dt.compareTo(returnDt);
		assertTrue(dtDiff == 0);
	}
	
	
	@Test
	void getListofDates() {
		List<LocalDate> lstDates = dateUtil.getDateList(LocalDate.parse("2020-05-05"), 6);
		assertTrue(lstDates.size() == 6);
	}
	
	
	/*
	 * Test for JAKR 9/3/15 5 101%
	 */
	@Test
	void getToolChargesTest1() {
		StringBuilder sb = toolCharges.getCalculatedResult(5, LocalDate.parse("2015-09-03"), "JAKR", 101);
		assertTrue(sb.indexOf("Final charge - -$0.06") > 1);
	}
	
	/*
	 * Test for LADW 7/2/20 3 10%
	 */
	@Test
	void getToolChargesTest2() {
		StringBuilder sb = toolCharges.getCalculatedResult(3, LocalDate.parse("2020-07-02"), "LADW", 10);
		assertTrue(sb.indexOf("Final charge - $3.58") > 1);
	}
	
	/*
	 * Test for CHNS 7/2/15 5 25%
	 */
	@Test
	void getToolChargesTest3() {
		StringBuilder sb = toolCharges.getCalculatedResult(5, LocalDate.parse("2015-07-02"), "CHNS", 25);
		assertTrue(sb.indexOf("Final charge - $3.35") > 1);
	}
	
	
	/*
	 * Test for JAKD 9/3/15 6 0%
	 */
	@Test
	void getToolChargesTest4() {
		StringBuilder sb = toolCharges.getCalculatedResult(6, LocalDate.parse("2015-09-03"), "JAKD", 0);
		assertTrue(sb.indexOf("Final charge - $8.97") > 1);
	}
	
	/*
	 * Test for JAKR 7/2/15 9 0%
	 */
	@Test
	void getToolChargesTest5() {
		StringBuilder sb = toolCharges.getCalculatedResult(9, LocalDate.parse("2015-07-02"), "JAKR", 0);
		assertTrue(sb.indexOf("Final charge - $17.94") > 1);
	}
	
	
	/*
	 * Test for JAKR 7/2/20 4 50%
	 */
	@Test
	void getToolChargesTest6() {
		StringBuilder sb = toolCharges.getCalculatedResult(4, LocalDate.parse("2020-07-02"), "JAKR", 50);
		assertTrue(sb.indexOf("Final charge - $1.5") > 1);
	}

}
