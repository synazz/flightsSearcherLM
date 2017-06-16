package com.searcher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



public class CSVReader {
	
	private static final String COMMA = ",";

	public static  Map<Object,Object> readCsvFile(String fileName) {
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		
	    try{
	      File inputFile = new File(fileName);
	      InputStream inputStream = new FileInputStream(inputFile);
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	      

	      map = br.lines().skip(1).map(line -> line.split(COMMA)).collect(Collectors.toMap(line -> line[0], line -> line[1]));
	      br.close();
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return map;
	}
	
	public static  HashSet<Flight> readCsvFlights(String fileName) {
		
		HashSet<Flight> flights = new HashSet<>();
		
	    try{
	      File inputFile = new File(fileName);
	      InputStream inputStream = new FileInputStream(inputFile);
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	      

	      flights = br.lines().skip(1).map(mapToFlight).collect(Collectors.toCollection(HashSet::new));
	      br.close();
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return flights;
	}
	
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
