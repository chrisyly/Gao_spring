package com.example.weather.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.weather.WeatherAppProperties;
import com.example.weather.integration.ows.WeatherForecast;
import com.example.weather.integration.ows.WeatherService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WeatherSummaryController {

	private final WeatherService weatherService;

	private final WeatherAppProperties properties;

	public WeatherSummaryController(WeatherService weatherService, WeatherAppProperties properties) {
		this.weatherService = weatherService;
		this.properties = properties;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView conferenceWeather() {
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("Summary", getSummary());
		return new ModelAndView("Summary", model);
	}

	private Object getSummary() {
		List<WeatherForecastSummary> summary = new ArrayList<>();
		for (String request : this.properties.getRequests()) {
			String city = request.split("/")[0];
			String countryCode = request.split("/")[1];
			String numbersOfDays = request.split("/")[2];
			WeatherForecast forecast = this.weatherService.getWeatherForecast(countryCode, city, numbersOfDays);
			summary.add(createWeatherForecastSummary(forecast));
		}
		return summary;
	}



	private WeatherForecastSummary createWeatherForecastSummary(WeatherForecast forecast) {
		return new WeatherForecastSummary(forecast);
	}

}
