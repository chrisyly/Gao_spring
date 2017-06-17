package com.ucdavis.application.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ucdavis.application.WeatherRequest;

@Service
public class WeatherService {

	private static List<WeatherRequest> requestList = new ArrayList<WeatherRequest>();
	
	private static Long requestNumber = (long) 0;
	
	public List<WeatherRequest> retrieveRequestsByLocation(String location) {
		List<WeatherRequest> filteredRequest = new ArrayList<>();
		for (WeatherRequest request : requestList) {
			if (request.getLocation().equals(location))
				filteredRequest.add(request);
		}
		return filteredRequest;
	}
	
	public List<WeatherRequest> retrieveRequestByDate(String date) {
		List<WeatherRequest> filteredRequest = new ArrayList<>();
		for (WeatherRequest request : requestList) {
			if (request.getDate().equals(date))
				filteredRequest.add(request);
		}
		return filteredRequest;
	}
	
	public WeatherRequest retrieveOneRequestBy(String location, String date) {
		try {
			for (WeatherRequest request : requestList) {
				if (request.getDate().equals(date) && request.getLocation().equals(location)) {
					return request;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
		}
		return null;
	}
	
	public void addRequest(String location, String date, Double temperature) {
		requestList.add(new WeatherRequest(++requestNumber, location, date,temperature));
	}
	
	public void deleteRequestByLocation(String location) {
		Iterator<WeatherRequest> iterator = requestList.iterator();
		while (iterator.hasNext()) {
			WeatherRequest request = iterator.next();
			if(request.getLocation().equals(location)) {
				iterator.remove();
			}
		}
	}
	
	public String[] getListOfDays(String start, String end) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<String> dates = new ArrayList<String>();
		
		Date startDate = dateFormat.parse(start);
		Date endDate = dateFormat.parse(end);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		while (calendar.getTime().before(endDate)) {
		    dates.add(dateFormat.format(calendar.getTime()));
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		String[] datesArray = dates.toArray(new String[0]);
		return datesArray;
	}
	public Double calculateSumOfTemperaturesOfOneCity(WeatherService service, String city, String startDate, String endDate) throws ParseException {
		Double result = (double) 0;
		String[] dates = service.getListOfDays(startDate, endDate); // 
		List<WeatherRequest> filteredRequest = new ArrayList<>();
		
		for (String date : dates) {
			WeatherRequest inquiry = service.retrieveOneRequestBy(city, date);
			filteredRequest.add(inquiry);
			result =+ inquiry.getTemperature();
		}
		
		return result;
	}
}
