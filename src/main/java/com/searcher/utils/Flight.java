package com.searcher.utils;



public class Flight  {

	private String flightCode;
	private String origin;
	private String destination;
	private Double basePrice;
	private String airline;

	
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


	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Double getBasePrice() {
		return basePrice;
	}
	
	public String getAirline() {
		return airline;
	}


	public void setAirline(String airline) {
		this.airline = airline;
	}

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