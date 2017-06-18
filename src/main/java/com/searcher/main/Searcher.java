/**
 * 
 * @author almudena claudio synazz@gmail.com
 * 
 * Simple flight search
 *
 */

package com.searcher.main;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.searcher.utils.CSVReader;
import com.searcher.utils.Config;
import com.searcher.utils.Flight;



public class Searcher {

	private static final String CHILD = "child";
	private static final String ADULT = "adult";

	/**
	 * 
	 * @param args
	 * 
	 * Parameters expected: 
	 * airport of origin, 
	 * airport of destination, 
	 * days to departure, 
	 * number of adult, child and infant passengers
	 * 
	 * Print a list of flight codes matching the route and total flight price for all passengers
	 * 
	 */
	public static void main(String[] args) {
		
		System.out.println(filter(CSVReader.readCsvFlights(),"Barcelona","Madrid"));
		
		if (args.length < 4) {
			System.out.println("Parameters expected:");
			System.out.println("Origin(String)");
			System.out.println("Destination(String)");
			System.out.println("Days_to_departure(Integer)");
			System.out.println("Number_of_adults(Integer)");
			System.out.println("[Number_of_children(Integer)]");
			System.out.println("[Number_of_infants(Integer)]");

		} else {
			String origin = args[0];
			String destination = args[1];
			Integer daysToDeparture = Integer.valueOf(args[2]);
			Integer numAdults = Integer.valueOf(args[3]);
			Integer numChildren = 0;
			Integer numInfants = 0;
			if (args.length > 4) {
				numChildren = Integer.valueOf(args[4]);
				if (args.length > 5) {
					numInfants = Integer.valueOf(args[5]);
				}
			}

			
			HashMap<String, Double> queryResultList = search(origin,
					destination, daysToDeparture, numAdults, numChildren,
					numInfants);

			if (queryResultList.size() == 0) {
				System.out.println("no flights available");
			} else {
				queryResultList.forEach((k, v) -> System.out
						.println("flight code: " + k + ", total price: " + v));
			}

		}

	}

	/**
	 * Return a map of flights matching the query (origin and destination airports) with the total price
	 * 
	 * @param origin
	 * @param destination
	 * @param daysToDeparture
	 * @param numAdults
	 * @param numChildren
	 * @param numInfants
	 * 
	 * @return a map of flights that match the query and their total price
	 * (flight code and total price in euro)
	 */
	public static HashMap<String, Double> search(String origin,
			String destination, Integer daysToDeparture, Integer numAdults,
			Integer numChildren, Integer numInfants) {

		HashMap<String, Double> queryResultList = new HashMap<String, Double>();

		double price = 0;
		double daysFactor = getDaysFactor(daysToDeparture);

		HashSet<Flight> flights = CSVReader.readCsvFlights();

		HashSet<Flight> flightsThatMatchTheQuery = filter(flights, origin,
				destination);

		for (Flight flight : flightsThatMatchTheQuery) {
			price = getTotalPrice(flight.getBasePrice(), numAdults,
					numChildren, numInfants, daysFactor,
					getAirlineCode(flight.getFlightCode()));

			queryResultList.put(flight.getFlightCode(), price);
		}

		return queryResultList;

	}

	/**
	 * Return a list of flights that match the origin and destination airports provided
	 * 
	 * @param flights (resource flights.csv with the data of all flights)
	 * @param origin
	 * @param destination
	 * 
	 * @return a list of flights
	 */
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

	/**
	 * Return the total price of a flight 
	 * 
	 * @param basePrice
	 * @param numAdults
	 * @param numChildren
	 * @param numInfants
	 * @param daysFactor
	 * @param airline
	 * 
	 * @return total price in euro
	 */
	public static double getTotalPrice(Double basePrice, Integer numAdults,
			Integer numChildren, Integer numInfants, double daysFactor,
			String airline) {

		HashMap<Object, Object> discounts = (HashMap<Object, Object>) CSVReader
				.readCsvFile(Config.getInstance()
						.getDiscountsByPassengerTypeFileName());

		// infants (fixed price)
		Double price = numInfants * getInfantPrice(airline);
		// other passengers
		price += basePrice
				* (100 - Double.valueOf(discounts.get(ADULT).toString())) / 100
				* numAdults * daysFactor;
		price += basePrice
				* (100 - Double.valueOf(discounts.get(CHILD).toString())) / 100
				* numChildren * daysFactor;

		price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		return price;
	}

	/**
	 * Return the airline code 
	 * 
	 * @param flightCode
	 * 
	 * @return airline code
	 */
	public static String getAirlineCode(String flightCode) {
		return flightCode.substring(0, 2);
	}

	/**
	 * The total price depends on the number of days to departure date.
	 * This method return a number that must be multiplied by the base price to get the price affected by date.
	 * 
	 * @param daysToDeparture
	 * @return 'daysfactor'
	 */
	public static double getDaysFactor(Integer daysToDeparture) {

		HashMap<Object, Object> map = (HashMap<Object, Object>) CSVReader
				.readCsvFile(Config.getInstance()
						.getPercentagesOfBasePriceFileName());

		TreeMap<Integer, Integer> percentages_of_base_price = new TreeMap<Integer, Integer>();
		for (Entry<Object, Object> entry : map.entrySet()) {
			percentages_of_base_price.put(
					Integer.valueOf(entry.getKey().toString()),
					Integer.valueOf(entry.getValue().toString()));
		}

		Double percentage = null;
		for (Entry<Integer, Integer> entry : percentages_of_base_price
				.entrySet()) {
			if (entry.getKey() <= daysToDeparture) {
				percentage = Double.valueOf(entry.getValue().toString());
			} else {
				break;
			}
		}

		return (percentage / 100);

	}

	/**
	 * The price of infants passenger are fixed. 
	 * @param airline
	 * @return fixed price
	 */
	public static Double getInfantPrice(String airline) {
		return Double.valueOf((String) CSVReader.readCsvFile(
				Config.getInstance().getInfantPricesFileName()).get(airline));
	}

	/**
	 * Return the IATA code of an airline 
	 * @param airline
	 * @return IATA code
	 */
	
	public static String getIATACode(String city) {
		return (String) CSVReader.readCsvFile(
				Config.getInstance().getAirportsFileName()).get(city);
	}

}
