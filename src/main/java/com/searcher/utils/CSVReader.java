
/**
 * 
 * @author almudena claudio synazz@gmail.com
 * 
 * Read csv files and map to a set of objects or hashmap
 * 
 */

package com.searcher.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;




public class CSVReader {
	
	private static final String COMMA = ",";
	static CSVReader csvreader = new CSVReader();
	static ClassLoader classLoader = csvreader.getClass().getClassLoader();

	
	/**
	 * Read a csv file and return a map
	 * 
	 * @param fileName
	 * 
	 * @return map (key,value)
	 */
	public static  Map<Object,Object> readCsvFile(String fileName) {
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		
	    try{

	      BufferedReader br = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(fileName)));

	      map = br.lines().skip(1).map(line -> line.split(COMMA)).collect(Collectors.toMap(line -> line[0], line -> line[1]));
	      br.close();
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return map;
	}
	
	/**
	 * Read a csv file and map to a set of objects of type Flight
	 * 
	 * @return set of objects of type Flight
	 */
	public static  HashSet<Flight> readCsvFlights() {
		
		
		HashSet<Flight> flights = new HashSet<>();
		
	    try{
	      BufferedReader br = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("flights.csv")));
	      
	      flights = br.lines().skip(1).map(mapToFlight).collect(Collectors.toCollection(HashSet::new));
	      br.close();
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return flights;
	}
	
	/**
	 * Map a line of a csv file to an object of type Flight
	 */
	private static  Function<String, Flight> mapToFlight = (line) -> {
		  String[] record = line.split(COMMA);
		  Flight flight = new Flight();
		  
		  
		  if (record[0] != null && record[0].trim().length() > 0) {
			  flight.setOrigin(record[0]);
		  }
		  if (record[1] != null && record[1].trim().length() > 0) {
			  flight.setDestination(record[1]);
		  }
		  if (record[2] != null && record[2].trim().length() > 0) {
			  flight.setFlightCode(record[2]);
		  }
		  if (record[3] != null && record[3].trim().length() > 0) {
			  flight.setBasePrice(Double.valueOf(record[3]));
		  }

		  return flight;
	};

}
