package com.cardinalfinance.test.repo;

/*
 * This is generic datasource interface so
 * underlying source can be different if 
 * the data is now being sourced from.
 */
public interface DataSource {
	public final static String TOOL = "tools";
	public final static String CHARGE = "charges";
	
	public <T>T getData(String sourceName);

}
