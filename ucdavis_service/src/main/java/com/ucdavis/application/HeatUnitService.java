package com.ucdavis.application;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HeatUnitService {
	
	private int locationId;
	
	private int startDate;
	
	private int endDate;
	
	private Double SumOfHeatUnit;
	
	private List<Double> heatUnit;

	public HeatUnitService() {
		super();
	}

	public HeatUnitService(int locationId, int startDate, int endDate) {
		super();
		this.locationId = locationId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the locationId
	 */
	public final int getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public final void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the startDate
	 */
	public final int getStartDate() {
		return startDate;
	}

	/**
	 * Set the start date
	 * @param startDate the startDate to set
	 */
	public final void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public final int getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public final void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the heatUnit
	 */
	public final List<Double> getHeatUnit() {
		return heatUnit;
	}
	
	
	/**
	 * default setHeatUnit
	 * @param locationId the location id to set
	 * @param startDate the start date to set
	 * @param endDate the end date to set
	 */
	public final void setHeatUnit() {
		this.heatUnit = this.getDataList(this.locationId, this.startDate, this.endDate);
	}

	/**
	 * @param heatUnit the heatUnit to set
	 */
	public final void setHeatUnit(List<Double> heatUnit) {
		this.heatUnit = heatUnit;
	}
	
	/**
	 * setHeatUnit change all parameters
	 * @param locationId the location id to set
	 * @param startDate the start date to set
	 * @param endDate the end date to set
	 */
	public final void setHeatUnit(int locationId, int startDate, int endDate) { // set Heat Unit by 3 params
		this.locationId = locationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.heatUnit =  this.getDataList(locationId, startDate, endDate);
	}
	
	/**
	 * setHeatUnit change locationId
	 * @param locationId the location id to set
	 */
	public final void setHeatUnit(int locationId) {// change location
		this.locationId = locationId;
		this.heatUnit = this.getDataList(locationId, this.startDate, this.endDate);
	}
	
	/**
	 * setHeatUnit change locationId and endDate
	 * @param locationId the location id to set
	 * @param endDate the endDate to set
	 */
	public final void setHeatUnit(int locationId, int endDate) { // change end date
		this.locationId = locationId;
		this.endDate = endDate;
		this.heatUnit = this.getDataList(locationId, this.startDate, endDate);
	}
	
	/**
	 * update List heatUnit by startDate and endDate
	 * @param startDate the startDate to set
	 * @param endDate the endDate to set
	 */
	public final void updateList(int startDate, int endDate) { // update new data
		this.startDate = startDate;
		this.endDate = endDate;
		this.heatUnit = this.getDataList(this.locationId, startDate, endDate);
	}
	
	/**
	 * get the heatUnit set by specified CSV file
	 * @param locationId the location of data
	 * @param startDate the startDate of data
	 * @param endDate the endDate of data
	 * @return the list of heatUnit
	 */
	private List<Double> getDataList(int locationId, int startDate, int endDate) { // get data from CSV file
		String fileName = "/Users/Mushi/Desktop/1/demoCSV.csv";
		File file = new File(fileName);
		
		// 2-dimensional array
		List<List<String>> lines = new ArrayList<>();
		List<String> inquiries = new ArrayList<>();
		List<Double> result = new ArrayList<>();
		Scanner inputStream;
		try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }
            inputStream.close();
            
            inquiries = lines.get(locationId).subList(startDate, endDate + 1);
            
            // String -> Double
            for (String inquiry : inquiries){
            	result.add(Double.valueOf(inquiry));
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		return result;
	}

	/**
	 * calculate the sum of heat unit by List heatUnit
	 * @param heatUnits the list of heatUnit
	 * @return the floating sum of heat units
	 */
	private double calculateSumOfHeatUnit(List<Double> heatUnits) {
		double sum = 0;
		for (Double heatUnit : heatUnits) {
			sum += heatUnit;
		}
		return sum;
	}

	/**
	 * @return the sumOfHeatUnit
	 */
	public final Double getSumOfHeatUnit() {
		return SumOfHeatUnit;
	}

	/**
	 * @param sumOfHeatUnit the sumOfHeatUnit to set
	 */
	public final void setSumOfHeatUnit() {
		this.SumOfHeatUnit = calculateSumOfHeatUnit(this.heatUnit);
	}
	
	/**
	 * print the heat units
	 */
	public void printHeatUnit() {
		System.out.println(this.getHeatUnit().toString());
		System.out.println("The number of days is: " + this.heatUnit.size());
	}
	
	/**
	 * print the sum of heat units
	 */
	public void printSumOfHeatUnit() {
		this.setSumOfHeatUnit();
		System.out.println("sum is "+ this.getSumOfHeatUnit().toString());
	}
}

