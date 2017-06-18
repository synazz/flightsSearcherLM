package com.searcher.main;


import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.searcher.main.Searcher;
import com.searcher.utils.CSVReader;
import com.searcher.utils.Config;

public class SearcherTest {


	private static final Object ADULT = "adult";
	private static final Object CHILD = "child";

	static SearcherTest csvreader = new SearcherTest();
	static ClassLoader classLoader = csvreader.getClass().getClassLoader();
	
	@Test
	public void testSearch1() {
		HashMap<String,Double> mapExpected = new HashMap<String,Double>();
		mapExpected.put("TK2372", 157.60);
		mapExpected.put("TK2659", 198.40);
		mapExpected.put("LH5909", 90.40);
		
		HashMap<String,Double> mapGot = Searcher.search("Amsterdam","Frankfurt",31,1,0,0);
		
        Assert.assertEquals(mapExpected, mapGot);
	}
	
	@Test
	public void testSearch2() {
		HashMap<String,Double> mapExpected = new HashMap<String,Double>();
		mapExpected.put("TK8891", 806.00);
		mapExpected.put("LH1085", 481.19);
		
		HashMap<String,Double> mapGot = Searcher.search("London","Istanbul",15,2,1,1);
		
        Assert.assertEquals(mapExpected, mapGot);
	}
	
	@Test
	public void testSearch3() {
		HashMap<String,Double> mapExpected = new HashMap<String,Double>();
		mapExpected.put("IB2171", 909.09);
		mapExpected.put("LH5496", 1028.43);
		
		HashMap<String,Double> mapGot = Searcher.search("Barcelona","Madrid",2,1,2,0);
		
        Assert.assertEquals(mapExpected, mapGot);
	}
	
	@Test
	public void testSearch4() {
		HashMap<String,Double> mapExpected = new HashMap<String,Double>();
		
		HashMap<String,Double> mapGot = Searcher.search("Paris","Frankfurt",2,1,2,0);
		
        Assert.assertEquals(mapExpected, mapGot);
	}
	
	
	@Test
	public void testGetInfantPrice() throws Exception{
	    Assert.assertEquals(10.00, Searcher.getInfantPrice("IB"),0.00);
	    Assert.assertEquals(15.00, Searcher.getInfantPrice("BA"),0.00);
	    Assert.assertEquals(7.00, Searcher.getInfantPrice("LH"),0.00);
	    Assert.assertEquals(20.00, Searcher.getInfantPrice("FR"),0.00);
	    Assert.assertEquals(10.00, Searcher.getInfantPrice("VY"),0.00);
	    Assert.assertEquals(5.00, Searcher.getInfantPrice("TK"),0.00);
	    Assert.assertEquals(19.90, Searcher.getInfantPrice("U2"),0.00);
	}
	
	@Test
	public void testGetIATACode() throws Exception{
	    Assert.assertEquals("MAD", Searcher.getIATACode("Madrid"));
	    Assert.assertEquals("BCN", Searcher.getIATACode("Barcelona"));
	    Assert.assertEquals("LHR", Searcher.getIATACode("London"));
	    Assert.assertEquals("CDG", Searcher.getIATACode("Paris"));
	    Assert.assertEquals("FRA", Searcher.getIATACode("Frankfurt"));
	    Assert.assertEquals("IST", Searcher.getIATACode("Istanbul"));
	    Assert.assertEquals("AMS", Searcher.getIATACode("Amsterdam"));
	    Assert.assertEquals("FCO", Searcher.getIATACode("Rome"));
	    Assert.assertEquals("CPH", Searcher.getIATACode("Copenhagen"));
	}
	
	@Test
	public void testGetDaysFactor() throws Exception{
	    Assert.assertEquals(1.50, Searcher.getDaysFactor(0),0.00);
	    Assert.assertEquals(1.50, Searcher.getDaysFactor(2),0.00);
	    Assert.assertEquals(1.20, Searcher.getDaysFactor(3),0.00);
	    Assert.assertEquals(1.20, Searcher.getDaysFactor(15),0.00);
	    Assert.assertEquals(1.00, Searcher.getDaysFactor(16),0.00);
	    Assert.assertEquals(1.00, Searcher.getDaysFactor(30),0.00);
	    Assert.assertEquals(0.80, Searcher.getDaysFactor(31),0.00);
	    Assert.assertEquals(0.80, Searcher.getDaysFactor(100),0.00);
		
	}
	
	@Test
	public void testGetTotalPrice() throws Exception{
	    
	    HashMap<Object,Object> discounts = (HashMap<Object, Object>) CSVReader
				.readCsvFile(Config.getInstance().getDiscountsByPassengerTypeFileName());
	    Assert.assertTrue(discounts.get(ADULT)!=null&&Double.valueOf(discounts.get(ADULT).toString())>=0);
	    Assert.assertTrue(discounts.get(CHILD)!=null&&Double.valueOf(discounts.get(CHILD).toString())>=0);
	    Assert.assertEquals(10.0, Searcher.getTotalPrice(295.00,0,0,1,0,"IB"),0.00);
	    Assert.assertEquals(10.0, Searcher.getTotalPrice(295.00,0,0,1,50,"IB"),0.00);

		
	}
	
	@Test
	public void testGetAirlineCode() throws Exception{
	    Assert.assertTrue("TK".equals(Searcher.getAirlineCode("TK2372")));
	    Assert.assertTrue("LH".equals(Searcher.getAirlineCode("LH5909")));
	    Assert.assertTrue("IB".equals(Searcher.getAirlineCode("IB2171")));
	    Assert.assertTrue("U2".equals(Searcher.getAirlineCode("U23631")));
	    Assert.assertTrue("FR".equals(Searcher.getAirlineCode("FR7521")));
	    Assert.assertTrue("VY".equals(Searcher.getAirlineCode("VY4335")));
	    Assert.assertTrue("BA".equals(Searcher.getAirlineCode("BA1164")));

		
	}


}
