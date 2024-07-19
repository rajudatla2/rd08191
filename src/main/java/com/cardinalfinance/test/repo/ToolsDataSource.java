package com.cardinalfinance.test.repo;

import java.io.File;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


import com.cardinalfinance.test.data.ToolChargesData;
import com.cardinalfinance.test.data.ToolsData;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class ToolsDataSource implements DataSource{
	Logger logger = LogManager.getLogger(ToolsDataSource.class);
	
	@Value("${com.cardinalfinancial.tools}")
	private String toolsDataFile;
	
	@Value("${com.cardinalfinancial.tool.charge}")
	private String toolChargeFile;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	ObjectMapper objectMapper;


	@Override
	public <T> T getData(String sourceName) {
		if (sourceName.equalsIgnoreCase(TOOL)) {
			return (T) getToolsData();
		} else if (sourceName.equalsIgnoreCase(CHARGE)) {
			return (T) getChargesData();
		} else {
			return null;
		}
	}
	
	//Load the json file into tools json object
	private ToolsData getToolsData() {
		
		ToolsData tData = new ToolsData();
		
		try {
		     tData =  objectMapper.readValue(getFile(toolsDataFile),ToolsData.class);
		} catch (Exception e) {
			
			System.out.println("Error parsing object due to " + e.getMessage());
			
		}
		return tData;
	}
	
	private ToolChargesData getChargesData() {
		ToolChargesData tChargeData = null;
		try {
		     tChargeData =  objectMapper.readValue(getFile(toolChargeFile),ToolChargesData.class);
		} catch (Exception e) {
			
			System.out.println("Error parsing object due to " + e.getMessage());
			
		}
		return tChargeData;
		
	}
	
	
	private File getFile(String filePath) {
		 File file = null;
		try {
			 Resource resource = resourceLoader.getResource("classpath:" + filePath);
		     file = resource.getFile();
		     
		} catch (Exception e) {
			
			System.out.println("Error parsing file  Due to " + e.getMessage());
			
		}
		return file;
	}

}
