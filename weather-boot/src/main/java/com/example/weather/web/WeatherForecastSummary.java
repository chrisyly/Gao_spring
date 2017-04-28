package com.example.weather.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.weather.integration.ows.Weather;
import com.example.weather.integration.ows.WeatherEntry;
import com.example.weather.integration.ows.WeatherForecast;

class WeatherForecastSummary {

		private final String country;

		private final String city;
		
		private final List<String> dates;

		private final List<Integer> codes;

		private final List<String> icons;

		private final List<Double> temps;
		
		private final List<WeatherEntry> entries;
	
		
		WeatherSummary(String country, String city, WeatherForecast forecast) {
			this.country = country;
			this.city = city;
			this.entries = forecast.getEntries();
			for(WeatherEntry entry : entries) {
				this.dates.add(entry.getTimestamp().toString());
				this.codes.add(entry.getWeatherId());
				this.icons.add(entry.getWeatherIcon());
				this.temps.add(entry.getTemperature());
			}
		}


		/**
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}


		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}


		/**
		 * @return the dates
		 */
		public List<String> getDates() {
			return dates;
		}


		/**
		 * @return the codes
		 */
		public List<Integer> getCodes() {
			return codes;
		}


		/**
		 * @return the icons
		 */
		public List<String> getIcons() {
			return icons;
		}


		/**
		 * @return the temps
		 */
		public List<Double> getTemps() {
			return temps;
		}
		
		
		
		
}
