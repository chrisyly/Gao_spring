package com.ucdavis.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateRequest {
	
	private static Integer RequestNumber;

	private String BloomDate;
	
	private String PredictDate;
	
	private String[] ListOfDays;
	
	private Integer NumberOfDays;

	public DateRequest(String bloomDate, String predictDate) throws ParseException {
		this.BloomDate = bloomDate;
		this.PredictDate = predictDate;
		this.ListOfDays= GetListOfDaysBy(this.BloomDate, this.PredictDate);
		this.NumberOfDays = ListOfDays.length;
		DateRequest.RequestNumber++;
	}

	/**
	 * @return the bloomDate
	 */
	public String getBloomDate() {
		return BloomDate;
	}

	/**
	 * @param bloomDate the bloomDate to set
	 */
	public void setBloomDate(String bloomDate) {
		BloomDate = bloomDate;
	}

	/**
	 * @return the predictDate
	 */
	public String getPredictDate() {
		return PredictDate;
	}

	/**
	 * @param predictDate the predictDate to set
	 */
	public void setPredictDate(String predictDate) {
		PredictDate = predictDate;
	}

	/**
	 * @return the listOfDays
	 * @throws ParseException 
	 */
	public String[] getListOfDays() throws ParseException {
		this.ListOfDays= GetListOfDaysBy(this.BloomDate, this.PredictDate);
		return ListOfDays;
	}

	/**
	 * @return the numberOfDays
	 */
	public Integer getNumberOfDays() {
		this.NumberOfDays = ListOfDays.length;
		return NumberOfDays;
	}
	
	private String[] GetListOfDaysBy(String dateString1, String dateString2) throws ParseException  {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<String> dates = new ArrayList<String>();

		Date startDate = dateFormat.parse(dateString1);
		Date endDate = dateFormat.parse(dateString2);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		while (calendar.getTime().before(endDate)) {
		    dates.add(dateFormat.format(calendar.getTime()));
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		//result
		String[] datesArray = dates.toArray(new String[0]);
		
		return datesArray;
	}

	public DateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the requestNumber
	 */
	public static Integer getRequestNumber() {
		return RequestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public static void setRequestNumber(Integer requestNumber) {
		RequestNumber = requestNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DateRequest [BloomDate=" + BloomDate + ", PredictDate=" + PredictDate + ", NumberOfDays=" + NumberOfDays
				+ "]";
	}
}