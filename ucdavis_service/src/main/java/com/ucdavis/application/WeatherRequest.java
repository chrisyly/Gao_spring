package com.ucdavis.application;

public class WeatherRequest {

	private String location;
	
	private String date;
	
	private Double temperature;

	public WeatherRequest(Long requestNumber, String location, String date, Double temperature) {
		super();
		this.location = location;
		this.date = date;
		this.temperature = temperature;
	}

	public WeatherRequest() {
		super();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
	@Override
	public String toString() {
		return "WeatherRequest [location=" + location + ", date=" + date + ", temperature=" + temperature + "]";
	}

	
}
