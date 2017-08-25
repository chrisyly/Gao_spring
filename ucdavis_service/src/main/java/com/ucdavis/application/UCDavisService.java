package com.ucdavis.application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Mushi
 * Aug.19th
 */
public class UCDavisService {
	
	public Map<String, Map<String, Map<String, String>>> data = new HashMap<>();

	public UCDavisService() {}
	
	// read from local CSV, TODO : debug mode, read local csv only....
	public UCDavisService(String todo) {
	}
	
	// save data into local Map:data
	public void getDataFromList(List<JSONObject> records) {
	}
	
	/**
	 * get data mapping from local csv
	 * @param csvPath
	 * @return Map<String, Map<String, String>> targetAndData
	 * Updated: Aug.21st, Mushi
	 */
	// Aug.22nd
	public void getDataMapFromCSV(String csvPath) {
		Map<String, Map<String, String>> dateMap = new HashMap<>();
		BufferedReader br = null;
        String splitToken = ",";
        
        try {
            br = new BufferedReader(new FileReader(csvPath));
            String line;
            String[] titles = br.readLine().split(splitToken);
            List<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
            	lines.add(line);
            }
        	for (int i = 0; i < titles.length; i++) {
            	Map<String, String> dataItemMap = new HashMap<>();
        		for (int j = 1; j < lines.size(); j++) {
        			String[] dataItems = lines.get(j).split(splitToken);
            		dataItemMap.put(dataItems[Arrays.asList(titles).indexOf("Date")], dataItems[i]);
            	}
        		dateMap.put(titles[i], dataItemMap);
        		
            }
            csvPath = csvPath.replaceAll(".*/", "");
            if (data.containsKey(csvPath.replace(".csv", ""))) {
            	data.get(csvPath.replace(".csv", "")).putAll(dateMap);
            } else {
            	data.put(csvPath.replace(".csv", ""), dateMap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void getDataMapFromCSV(File file) {
		getDataMapFromCSV(file.getAbsolutePath());
	}
	
	public void readLocalCsv() {
		Path currentPath = Paths.get("");
		String folderPath = currentPath.toAbsolutePath().toString();
		//System.out.println(folderPath);
		for (final File file : new File(folderPath).listFiles()) {
			//System.out.println(file.getName());
			if (file.getName().contains(".csv")) {
				getDataMapFromCSV(file);
			}
		}
	}
	
	// TODO, database
	public void updateData() {
		
	}
	
	public void writeToMap(List<JSONObject> records, String target) {
		Map<String, Map<String,String>> dataItems = new HashMap<>();
		Iterator<?> keys = records.get(0).keys();
		String key = "";
		while (keys.hasNext()) {
			key = (String) keys.next();
			Map<String, String> tmpBase = new HashMap<>();
			for (JSONObject record : records) {
				try {
					tmpBase.put(record.getString("Date"), record.getString(key));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println("TmpBase: " + tmpBase.toString());
			dataItems.put(key, tmpBase);
		}
		if (data.containsKey(target)) {
			data.get(target).putAll(dataItems);
		} else {
			data.put(target, dataItems);
		}
	}
	
	/**
	 * write List<JSONObject> to CSV, file name is the key of map
	 * return the activity of stations
	 *  * Updated: Aug.21st, Mushi
	 */
	public void writeToCSV(List<JSONObject> records, String fileName) {
		boolean isFirstRow = true;
		// empty
		StringBuilder csv = new StringBuilder();
		for (JSONObject record : records) {
			Iterator<?> itr = record.keys();
			// Column not created yet
			if (isFirstRow) {
				Iterator<?> columns = record.keys();
				while (columns.hasNext()) {
					csv.append((String)columns.next());
					csv.append(",");
				}
				csv.append("\n"); // first row item name done
				isFirstRow = false;
			}
			
			// get the values of all items
			while (itr.hasNext()) {
				String key = (String) itr.next();
				try {
					csv.append(record.get(key).toString());
					csv.append(",");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			csv.append("\n");
		}
		
		// csv
		if (csv.length() > 0) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new File(fileName+".csv"));
				pw.write(csv.toString());
		        pw.close();
		        System.out.println("csv file: <" + fileName + "> created");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String calculateHeatUnitResult(String query) {
		Map<String, String> dataMap = processQuery(query);
		System.out.println(dataMap.toString());
		Map<String, String> tempDate = new HashMap<>();
		// get query items
		String startDate = dataMap.get("start_date");
		String endDate = dataMap.get("end_date");
		String target = dataMap.get("targets");
		String hourly = dataMap.get("hourly");
		// converting dates
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = sdf.parse(startDate);
			Date end  = sdf.parse(endDate);
			if (hourly.equals("false")) {
				tempDate = data.get(target).get("DayAirTmpAvg");
			} else {} // hourly applied, TODO
			
			double totalHU = 0.0;
			StringBuilder resultBuilder = new StringBuilder(); 
			while (start.before(end) || start.equals(end)) {
				resultBuilder.append("Date:");
				resultBuilder.append(sdf.format(start) + ", ");
				resultBuilder.append("HeatUnit: ");
				Double heatUnit = 0.0;
				DecimalFormat df = new DecimalFormat("#.00");
				if (tempDate.get(sdf.format(start)) != null) heatUnit = Double.valueOf(df.format(Double.parseDouble(tempDate.get(sdf.format(start)))));
				totalHU = Double.valueOf(df.format(totalHU +  heatUnit)); 
				resultBuilder.append(heatUnit + ", ");
				resultBuilder.append("Total: ");
				resultBuilder.append(totalHU);
				resultBuilder.append("\n");
				// add one day
				start = sdf.parse((addOneDay(sdf.format(start))));
			}
			return resultBuilder.toString();
		} catch (ParseException e) {
			System.err.println("[FAIL]Fail to parse date string");
			e.printStackTrace();
		}

		
		return null;
	}

	/**
	 * parse the request string, and return map of params of request
	 * @param request
	 * @return
	 */
	public Map<String, String> processQuery(String request) {
		Map<String, String> queryMap = new HashMap<>();
		String keyWord;
		// check targets
		if (request.contains("targets")) {
			keyWord =  "targets";
			int index = request.indexOf(keyWord) + keyWord.length();
			// check if white space or '='
			while (index < request.length() && ((request.charAt(index) == '=') || Character.isWhitespace(request.charAt(index)))) index++;
			// find the end of value of targets
			int i = index;
			while (i < request.length() && Character.isDigit(request.charAt(i))) i++;
			queryMap.put("targets", request.substring(index, i));
		}
		
		// check start_date
		if (request.contains("start_date")) {
			keyWord = "start_date";
			int index = request.indexOf(keyWord) + keyWord.length();
			//check if white space and '='
			while (index < request.length() && ((request.charAt(index) == '=') || Character.isWhitespace(request.charAt(index)))) index++;
			queryMap.put(keyWord, request.substring(index, index + 10));
		}
		
		// check end_date
		if (request.contains("end_date")) {
			keyWord = "end_date";
			int index = request.indexOf(keyWord) + keyWord.length();
			while (index < request.length() && ((request.charAt(index) == '=') || Character.isWhitespace(request.charAt(index)))) index++;
			int i = index;
			queryMap.put(keyWord, request.substring(i, i + 10));
		}
		
		// check daily or hourly
		if (request.contains("hourly")) {
			keyWord = "hourly";
			int index = request.indexOf(keyWord) + keyWord.length();
			while (index < request.length() && ((request.charAt(index) == '=') || Character.isWhitespace(request.charAt(index)))) index++;
			if (request.substring(index, index + 5).contains("true")) {
				queryMap.put(keyWord, "true");
			} else {
				queryMap.put(keyWord, "false");
			}
		}
		
		// check the method TODO
		if (request.contains("method")) {
			keyWord = "method";
			// do nothing
		}
		
		return queryMap;
	}
	
	// getter and setter
	public Map<String, Map<String, Map<String, String>>> getData() {
		return data;
	}
	
	public String addOneDay(String date) {
		String newDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
			c.add(Calendar.DATE, 1);  // number of days to add
			newDate = sdf.format(c.getTime());  // dt is now the new date
			return newDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
}
