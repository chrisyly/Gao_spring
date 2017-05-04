package com.example.weather;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.weather")
public class WeatherAppProperties {

	@Valid
	private final Api api = new Api();

	/**
	 * Comma-separated list of locations to display. Each entry should have the
	 * form "Country/City".
	 */
	private List<String> requests = Arrays.asList("California/ISO3166-2:US/5", "Pittsburgh/ISO3166-2:US/3");

	public Api getApi() {
		return this.api;
	}

	public List<String> getRequests() {
		return this.requests;
	}

	public void setRequests(List<String> requests) {
		this.requests = requests;
	}

	public static class Api {

		/**
		 * API key of the OpenWeatherMap service.
		 */
		@NotNull
		private String key;

		public String getKey() {
			return this.key;
		}

		public void setKey(String key) {
			this.key = key;
		}

	}

}
