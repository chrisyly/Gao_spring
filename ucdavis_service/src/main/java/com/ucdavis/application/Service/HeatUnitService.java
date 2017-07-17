package com.ucdavis.application.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HeatUnitService {
	
	private String stationName;

	private String bloomDate;
	
	private String currentDate;
	
	private List<List<String>> dataList;
	
	private Double SumOfHeatUnit;
	
	private List<Double> heatUnit;
	
	private List<Object> prediction;
	
	private Double maxHeatUnit;

	
	public HeatUnitService() {
		super();
	}

	public HeatUnitService(String bloomDate, String currentDate, Double maxHeatUnit) {
		super();
		this.bloomDate = bloomDate;
		this.currentDate = currentDate;
		this.maxHeatUnit = maxHeatUnit;
	}
	
	/**
	 * @return the bloomDate
	 */
	public final String getBloomDate() {
		return bloomDate;
	}

	/**
	 * @param bloomDate the bloomDate to set
	 */
	public final void setBloomDate(String bloomDate) {
		this.bloomDate = bloomDate;
	}

	/**
	 * @return the currentDate
	 */
	public final String getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate the currentDate to set
	 */
	public final void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	

	/**
	 * @param maxHeatUnit the maxHeatUnit to set
	 */
	public final void setMaxHeatUnit(Double maxHeatUnit) {
		this.maxHeatUnit = maxHeatUnit;
	}
	
	/**
	 * @return the maxHeatUnit
	 */
	public final Double getMaxHeatUnit() {
		return maxHeatUnit;
	}

	public final void setSumOfHeatUnit() {
		this.SumOfHeatUnit = calculateSumOfHeatUnit(this.heatUnit);
	}
	
	public final void setPrediction() throws ParseException {
		this.prediction = predictMaxmimumFirmness(this.maxHeatUnit);
	}
	
	
	public final List<Double> getHeatUnit() {
		return heatUnit;
	}

	public final void setHeatUnit() throws ParseException {
		List<Double> result = new ArrayList<>();
		Date d1 = getDateFromString(this.bloomDate);
		Date d2 = getDateFromString(this.currentDate);
		
		while(d1.before(d2)) {
			result.add(getDataOnDay(d1));
			d1 = addOneDay(d1);
		}
		
		result.add(getDataOnDay(d2));
		
		this.heatUnit = result;
	}
	
	public final void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	
	/**
	 * @return the stationName
	 */
	public final String getStationName() {
		return stationName;
	}

	/**
	 * @return the sumOfHeatUnit
	 */
	public final Double getSumOfHeatUnit() {
		return SumOfHeatUnit;
	}

	/**
	 * @return the prediction
	 */
	public final List<Object> getPrediction() {
		return prediction;
	}


	public final List<List<String>> getDataList() {
		return dataList;
	}


	public final void setDataList() throws IOException {
		//String path = new java.io.File(".").getCanonicalPath();
		String path = "../resources/main/" + this.stationName + "_edited.csv";
		File file = new File(path);
		
		// csv --> list<List<string>>
		List<List<String>> lines = new ArrayList<>();
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
		}catch (FileNotFoundException e) {
            e.printStackTrace();
		}
		this.dataList = lines;
	}
	
	public ArrayList<Map<String, Double>> printDayWithSumHU() throws ParseException {
		ArrayList<Map<String, Double>> rst = new ArrayList<>();
		Date srt = getDateFromString(this.bloomDate);
		Date end = getDateFromString((String)this.getPrediction().get(0));
		Double hU = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		while(srt.before(end) || srt.equals(end)) {
			hU += getDataOnDay(srt);
			Map<String, Double> mapping = new HashMap<>();
			mapping.put(sdf.format(srt), hU);
			rst.add(mapping);
			srt = addOneDay(srt);
		}
		
		
		return rst;
	}
	
	private Double getDataOnDay(Date day) throws ParseException {
		Date zero = getDateFromString("20170101");

		int index = (int)((day.getTime() - zero.getTime()) / (1000 * 60 * 60 * 24) + 3);
		
		Double result = Double.valueOf(this.dataList.get(index).get(4)); // 4 is heat units
		
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
	
	
	private Date getDateFromString(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d1 = sdf.parse(date);
		return d1;
	}
	
	
	public List<Object> predictMaxmimumFirmness(Double totalTU) throws ParseException {
		List<Object> result = new ArrayList<>();
		setSumOfHeatUnit();
		Double heatUnitCount = this.SumOfHeatUnit;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date date = getDateFromString(this.currentDate);
		int dayCount = 0;

		if (heatUnitCount >= totalTU) {
			result.add(this.currentDate);
			result.add(dayCount);
		}
		
		while (heatUnitCount < totalTU) {
			date = addOneDay(date);
			heatUnitCount += getDataOnDay(date);
			dayCount++;
		}
		
		String predictDate = sdf.format(date);
		result.add(predictDate);
		result.add("Need " + dayCount + " more days");
		return result;
	}
	
	private Date addOneDay(Date dt) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		Date date = c.getTime();
		return date;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HeatUnitService\n [bloomDate=" + bloomDate + ",\n currentDate=" + currentDate + ",\n SumOfHeatUnit="
				+ SumOfHeatUnit + ",\n prediction=" + prediction + ",\n maxHeatUnit=" + maxHeatUnit + "]";
	}

}