package com.searcher.main;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.searcher.utils.CSVReader;
import com.searcher.utils.Flight;

public class Searcher {


	public static void main(String[] args) {
		if(args.length<4){
			System.out.println("Parameters expected:");
			System.out.println("Origin(String)");
			System.out.println("Destination(String)");
			System.out.println("Days_to_departure(Integer)");
			System.out.println("Number_of_adults(Integer)");
			System.out.println("[Number_of_children(Integer)]");
			System.out.println("[Number_of_infants(Integer)]");

		}else{
			String origin = args[0];
			String destination = args[1];
			Integer daysToDeparture = Integer.valueOf(args[2]);
			Integer numAdults = Integer.valueOf(args[3]);
			Integer numChildren = 0;
			Integer numInfants = 0;
			if(args.length>4){
				numChildren = Integer.valueOf(args[4]);
				if(args.length>5){
					numInfants = Integer.valueOf(args[5]);
				}
			}
			
			HashMap<String,Double> queryResultList = search(origin,destination,daysToDeparture,numAdults,numChildren,numInfants);
			
			if (queryResultList.size() == 0) {
				System.out.println("no flights available");
			} else {
				queryResultList.forEach((k,v) -> System.out.println("flight code: " + k + ", total price: " +  v)); 
			}
			
		}
		
	}



	public static HashMap<String,Double> search(String origin, String destination,
			Integer daysToDeparture, Integer numAdults, Integer numChildren,
			Integer numInfants) {
		
		HashMap<String,Double> queryResultList = new HashMap<String,Double>(); 
		
		double price = 0;
		double daysFactor = getDaysFactor(daysToDeparture);
		
        
        
		HashSet<Flight> flights = CSVReader
				.readCsvFlights("./resources/flights.csv");
		
		HashSet<Flight> flightsThatMatchTheQuery = filter(flights,origin,
				destination);

		for (Flight flight : flightsThatMatchTheQuery) {
			price = getTotalPrice(flight.getBasePrice() ,numAdults,numChildren,numInfants,daysFactor,getAirlineCode(flight.getFlightCode()));
			
			queryResultList.put(flight.getFlightCode(), price);
		}
		
		return queryResultList;
		
	}



	public static HashSet<Flight> filter(HashSet<Flight> flights,
			String origin, String destination) {
		
		HashSet<Flight> flightsThatMatchTheQuery = flights
				.stream()
				.filter(x -> x.getOrigin().equalsIgnoreCase(
						(getIATACode(origin))))
				.filter(y -> y.getDestination().equalsIgnoreCase(
						getIATACode(destination)))
				.collect(Collectors.toCollection(HashSet::new));

		return flightsThatMatchTheQuery;
	}

	public static double getTotalPrice(Double basePrice, Integer numAdults,
			Integer numChildren, Integer numInfants, double daysFactor, String airline) {
		
		HashMap<Object,Object> discounts = (HashMap<Object, Object>) CSVReader
				.readCsvFile(
						"./resources/discounts_by_passenger_type.csv");

		
		Double price = basePrice * (100-Double.valueOf(discounts.get("adult").toString()))/100 * numAdults * daysFactor;
		price += basePrice * (100-Double.valueOf(discounts.get("child").toString()))/100 * numChildren * daysFactor;
		price += numInfants * getInfantPrice(airline);
		
		price=new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return price;
	}
	
	private static String getAirlineCode(String flightCode) {
		return flightCode.substring(0, 2);
	}



	public static double getDaysFactor(Integer daysToDeparture) {
		
		HashMap<Object,Object> map = (HashMap<Object, Object>) CSVReader
				.readCsvFile(
						"./resources/percentages_of_base_price.csv");
		
		TreeMap<Integer, Integer> percentages_of_base_price = new TreeMap<Integer, Integer>();
		for(Entry<Object,Object> entry:map.entrySet()){
			percentages_of_base_price.put(Integer.valueOf(entry.getKey().toString()), Integer.valueOf(entry.getValue().toString()));
		}
		
		Double percentage = null;
		for(Entry<Integer,Integer> entry:percentages_of_base_price.entrySet()){
			if(entry.getKey()<=daysToDeparture){
				percentage = Double.valueOf(entry.getValue().toString());
			}else{
				break;
			}
		}
		
		return (percentage/100);
			
	}

	public static Double getInfantPrice(String airline) {
		return Double.valueOf((String) CSVReader
				.readCsvFile(
						"./resources/infant_prices.csv")
				.get(airline));
	}

	public static String getIATACode(String city) {
		return (String) CSVReader
				.readCsvFile(
						"./resources/airports.csv")
				.get(city);
	}


}
