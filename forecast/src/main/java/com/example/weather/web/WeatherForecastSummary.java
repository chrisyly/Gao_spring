package com.example.weather.web;

import java.util.List;

import com.example.weather.integration.ows.WeatherEntry;
import com.example.weather.integration.ows.WeatherForecast;

public class WeatherForecastSummary{

	private final List<WeatherEntry> entries;
	
	private final String city;
	
	private List<String> icons;
	
	private List<Double> temperatures;
	
	WeatherForecastSummary (WeatherForecast forecast) {
		this.city = forecast.getName();
		this.entries = forecast.getEntries();
		for (WeatherEntry entry :  entries) {
			icons.add(entry.getWeatherIcon());
			temperatures.add(entry.getTemperature());
		}
	}

	/**
	 * @return the entries
	 */
	public List<WeatherEntry> getEntries() {
		return entries;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the icons
	 */
	public List<String> getIcons() {
		return icons;
	}

	/**
	 * @return the temperatures
	 */
	public List<Double> getTemperatures() {
		return temperatures;
	}
	
	public String getFahrenheitTemperature(Double temperature) {
		double fahrenheitTemp = (temperature * 1.8) - 459.67;
		return String.format("%4.2f", fahrenheitTemp);
	}

	public String getCelsiusTemperature(Double temperature) {
		double celsiusTemp = temperature - 273.15;
		return String.format("%4.2f", celsiusTemp);
	}
	

}
