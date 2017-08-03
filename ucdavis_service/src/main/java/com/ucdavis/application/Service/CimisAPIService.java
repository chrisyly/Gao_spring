package com.ucdavis.application.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ucdavis.application.AppProperties;
import com.ucdavis.application.DateRequest;

public class CimisAPIService {

	private String URL = "http://et.water.ca.gov/api/data?";
	
	private String bloomDate;
	
	private String currentDate;
	
	private String APPKEY;
	
	private String zipcode;
	
	private String zipcodeExt;
	
	private String station;
	
	private String json;
	
	private Map<String, Object> convertedResultMap;
	
	private List<Double> heatUnits;
	
	private Double SumOfHeatUnit;

	private Double MaxHeatUnit;

	private List<Object> prediction;
	
	private JSONObject jsonObject;

	private ArrayList<ArrayList<ArrayList<String>>> arrayType = new ArrayList<>();
	
	public CimisAPIService(DateRequest dateRequest, AppProperties properties) {
		this.APPKEY = properties.getApi().getKey();
		this.bloomDate = dateRequest.getBloomDate();
		this.currentDate = dateRequest.getCurrentDate();
		this.zipcode = dateRequest.getStation().substring(0, 5);// 5 digit zip code
		this.zipcodeExt = dateRequest.getStation().substring(6, 10); //  4 digit extension
		this.station = dateRequest.getStation().substring(10,dateRequest.getStation().length()); // station name specified
	}

	public CimisAPIService() {
		super();
	}
	
	public Double getMaxHeatUnit() {
		return MaxHeatUnit;
	}

	public void setMaxHeatUnit(Double maxHeatUnit) {
		MaxHeatUnit = maxHeatUnit;
	}
	
	public String getBloomDate() {
		return bloomDate;
	}

	public void setBloomDate(String bloomDate) {
		this.bloomDate = bloomDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
	public Double getSumOfHeatUnit() {
		return SumOfHeatUnit;
	}
	
	public void setPrediction(List<Object> prediction) {
		this.prediction = prediction;
	}

	public void setPrediction() throws Exception {
		this.prediction = predictMaxmimumFirmness(this.MaxHeatUnit);
	}

	private List<Object> predictMaxmimumFirmness(Double maxHeatUnit) throws Exception {
		List<Object> result = new ArrayList<>();
		setSumOfHeatUnit();
		Double heatUnitCount = this.SumOfHeatUnit;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date curt = sdf.parse(this.currentDate);
		
		int dayCount = 0;
		
		// hit the max already
		if (heatUnitCount >= maxHeatUnit) {
			result.add(this.currentDate);
			result.add("Nuts are ready!");
		}
		
		while (heatUnitCount < maxHeatUnit) {
			curt = addOneDay(curt);
			this.setCurrentDate(sdf.format(curt));
			setResponse();
			heatUnitCount = calculateSumOfHeatUnit(this.heatUnits);
			dayCount++;
		}
		
		String predictDate = sdf.format(curt);
		result.add(predictDate);
		result.add("Need " + dayCount + " more days");
		return result;
	}

	public void setSumOfHeatUnit(Double sumOfHeatUnit) {
		SumOfHeatUnit = sumOfHeatUnit;
	}
	
	public void setSumOfHeatUnit() {
		this.SumOfHeatUnit = calculateSumOfHeatUnit(this.heatUnits);
	}

	private Double calculateSumOfHeatUnit(List<Double> heatUnits) {
		double sum = 0;
		for (Double heatUnit : heatUnits) {
			sum += heatUnit;
		}
		return sum;
	}
	
	public void setURL() throws ParseException {
		
		StringBuilder builder = new StringBuilder(this.getURL());
	
		builder.append("appKey=");
		builder.append(this.getAPPKEY());
		

		builder.append("&targets=");
		builder.append("2");
	
		
		builder.append("&startDate=");
		builder.append(this.getBloomDate());

		

		builder.append("&endDate=");
		builder.append(this.getCurrentDate());
		
		this.URL = builder.toString();
	}
	
	
	// JSON, jsonObject, HashMap, "Records", setHeatUnits
	public void setResponse() throws Exception{
		setURL();
		this.json = readUrl(this.URL);
		this.jsonObject = new JSONObject(this.json);
		this.convertedResultMap = jsonToMap(this.jsonObject);
		
		
		// refresh the heat units
		List<Map<String, Object>>records = returnRecordsBy(this.getConvertedResultMap());
		System.out.println(records.toString());
		
		setHeatUnits(getHeatUnitListBy(records));
		// converting CSV
		String currentResult = null;
		
		for (int i = 0; i < records.size(); i++) {
			Map<String, Object> temp = records.get(i); 
			ArrayList<ArrayList<String>> result = mapToList(temp);
			System.out.println(result.toString());
			this.getArrayType().add(result);
			
			if (i == 0) currentResult = addItems(result);
			
			currentResult = addCurrentResult(currentResult, result);
		}
		covertCSV(this, currentResult);
		// end of CSV
	}
	
	public List<Double> getHeatUnits() {
		return heatUnits;
	}

	private void setHeatUnits(List<Double> heatUnits) {
		this.heatUnits = heatUnits;
	}

	@SuppressWarnings("unchecked")
	private List<Double> getHeatUnitListBy(List<Map<String, Object>> records) {
		List<Map<String, Object>> recordType = (List) records;
  		List<Double> heatUnits = new ArrayList<>();
  		for (int i = 0; i < recordType.size(); i++) {
  			Map<String, Object> mapSet = recordType.get(i);
  			Iterator<String> itr = mapSet.keySet().iterator();
  			while (itr.hasNext()) {
  				String key1 = (String)itr.next();
  				if (key1.equals("DayAirTmpAvg")) {
  					Map<String, Object> tmpDetails = (Map<String, Object>) mapSet.get(key1);
  					Double temp = Double.parseDouble((String) tmpDetails.get("Value"));
  					Double avgTemperature = (temp - 32) * 5 / 9 - 7;
  					heatUnits.add(avgTemperature);
  					System.out.println("Avg Tmp is: " + avgTemperature);
  				}
  			}
  		}
  		return heatUnits;
	}

	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}

	/* parser not needed from Aug.3rd
	private String parseDateFormat(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat target = new SimpleDateFormat("yyyy-MM-dd");
		String rst = target.format(sdf.parse(date));
		
		return rst;
	}
	*/
	
	@SuppressWarnings("unchecked")
	private static List<Map<String, Object>> returnRecordsBy(Map<String, Object> convertedResultMap) {
		
		// get "Data"
		Map<String, Object> data = (Map<String, Object>) convertedResultMap.get("Data");
	  	// get "Providers"
		Object dataInquiry = data.get("Providers");
		Map<String, Object> providers = (Map<String, Object>) ((List<Object>) dataInquiry).get(0);
		List<Map<String, Object>> records = (ArrayList<Map<String, Object>>) providers.get("Records");
		
		return records;
	}
	
	public static ArrayList<ArrayList<String>> mapToList(Map<String, Object> map) {

		ArrayList<ArrayList<String>> list = new ArrayList<>();
		
		Iterator<String> keys = map.keySet().iterator();
		
		while(keys.hasNext()) {
			
			String key = (String) keys.next();
			Object tempValue = map.get(key);
			
			// not nested
			if (tempValue instanceof String) {
				ArrayList<String> element = new ArrayList<>();
				element.add(key);
				element.add((String)tempValue);
				list.add(element);
			}
			
			// nested
			if (tempValue instanceof Map) {
				
				@SuppressWarnings("unchecked")
				Map<String, String> templeMap = (Map<String, String>) tempValue;
				Iterator<String> subEntries = templeMap.keySet().iterator();
				while(subEntries.hasNext()) {
					ArrayList<String> element = new ArrayList<>();
					
					String subEntry = subEntries.next();
					String subTempValue = templeMap.get(subEntry);
					element.add(key + "." + subEntry);
					element.add(subTempValue);
					list.add(element);
				}
			}
		}
		return list;
	}

	private static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
	    Map<String, Object> retMap = new HashMap<String, Object>();
	    
	    if(json != JSONObject.NULL) {
	        retMap = ObjectToMap(json);
	    }
	    return retMap;
	}

	private static Map<String, Object> ObjectToMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    @SuppressWarnings("unchecked")
		Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = ObjectToList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = ObjectToMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	private static List<Object> ObjectToList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = ObjectToList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = ObjectToMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	private String addItems(ArrayList<ArrayList<String>> results) {
		StringBuilder items = new StringBuilder();
	  	
		for (ArrayList<String> result : results) {
			items.append(result.get(0));
			items.append(",");
		  	
		}
		
		return items.toString();
	}
	
	private String addCurrentResult(String previous, ArrayList<ArrayList<String>> results) {
		StringBuilder current  = new StringBuilder(previous);
	  	current.append("\n");
		
	  	for (ArrayList<String> result : results) {
	  		current.append(result.get(1));
	  		current.append(",");
	  	}
	  	
	  	return current.toString();
	}
	
	
	public static void covertCSV(CimisAPIService service, String stringBuilder) throws FileNotFoundException {
	  	PrintWriter pw = new PrintWriter(new File(service.getZipcode() + service.getStation()  + ".csv"));
	  	pw.write(stringBuilder);
        pw.close();
        System.out.println("done!");
	}
	
	private Date addOneDay(Date dt) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		Date date = c.getTime();
		return date;
	}

	// getter
	public final String getURL() {
		return URL;
	}
	
	public final String getAPPKEY() {
		return APPKEY;
	}

	public final String getZipcode() {
		return zipcode;
	}

	public final String getZipcodeExt() {
		return zipcodeExt;
	}

	public final String getStation() {
		return station;
	}

	public final String getJson() {
		return json;
	}

	public final Map<String, Object> getConvertedResultMap() {
		return convertedResultMap;
	}

	public final JSONObject getJsonObject() {
		return jsonObject;
	}

	public final ArrayList<ArrayList<ArrayList<String>>> getArrayType() {
		return arrayType;
	}

	public List<Object> getPrediction() {
		return prediction;
	}
	
}
