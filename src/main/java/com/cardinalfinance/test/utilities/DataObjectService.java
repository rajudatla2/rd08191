package com.cardinalfinance.test.utilities;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cardinalfinance.test.data.ToolChargesData;
import com.cardinalfinance.test.data.ToolsData;
import com.cardinalfinance.test.repo.DataSource;
import com.cardinalfinance.test.repo.ToolsDataSource;
// Load the Data one time so its available to 
//which ever object needs it 
@Service
public class DataObjectService {
	
	
	
	private  ToolsDataSource dataSource;
	
	private ToolsData tData;
	private ToolChargesData tcData;
	
	@Autowired
	private DataObjectService(ToolsDataSource dataSource) {
		this.tData = getToolsData(dataSource);
		this.tcData = getChargesData(dataSource);
	}
	
	
	private ToolsData getToolsData(ToolsDataSource dataSource) {
		return dataSource.getData(DataSource.TOOL);
	}
	
	
	private ToolChargesData getChargesData(ToolsDataSource dataSource) {
		return dataSource.getData(DataSource.CHARGE);
	}
	
	public ToolsData getTools() {
		return this.tData;
	}
	
	public ToolChargesData getCharges() {
		return this.tcData;
	}

}
