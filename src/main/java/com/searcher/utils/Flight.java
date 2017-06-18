/**
 * 
 * @author almudena claudio synazz@gmail.com
 * 
 * 
 */

package com.searcher.utils;



public class Flight  {

	private String flightCode;
	private String origin;
	private String destination;
	private Double basePrice;
	private String airline;

	
	/**
	 * @param flightCode
	 * @param origin
	 * @param destination
	 * @param basePrice
	 */
	
	public Flight(String flightCode, String origin, String destination,
			Double basePrice) {
		super();
		this.flightCode = flightCode;
		this.origin = origin;
		this.destination = destination;
		this.basePrice = basePrice;
	}
	
	public Flight() {
	}

	/**
	 * @return flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * @param flightCode
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	/**
	 * 
	 * @return origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * 
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * 
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 
	 * @return basePrice
	 */
	public Double getBasePrice() {
		return basePrice;
	}
	
	/**
	 * 
	 * @return airline
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * 
	 * @param airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * 
	 * @param basePrice
	 */
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	

	@Override
	public String toString() {
		return "Flight [flightCode=" + flightCode + ", origin=" + origin
				+ ", destination=" + destination + ", basePrice=" + basePrice
				+ "]";
	}



	
	
}