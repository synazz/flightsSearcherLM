package com.searcher.utils;

import java.util.Properties;

public class Config
{
	private static final Config instance = new Config();
	private Properties properties = new Properties();
	
	public Config(){
		try
		{
			properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties"));
		}
		catch (Exception e)
		{
	    	e.printStackTrace();
		}
		
	}
	
	public static Config getInstance(){
		return instance;
	}

	public String getAirportsFileName(){
		return properties.getProperty("airports_filename");
	}
	
	public String getDiscountsByPassengerTypeFileName(){
		return properties.getProperty("discounts_by_passengertype_filename");
	}
	
	public String getFlightsFileName(){
		return properties.getProperty("flights_filename");
	}

	public String getInfantPricesFileName(){
		return properties.getProperty("infantprices_filename");
	}	

	public String getPercentagesOfBasePriceFileName(){
		return properties.getProperty("percentages_of_baseprice_filename");
	}	

}
