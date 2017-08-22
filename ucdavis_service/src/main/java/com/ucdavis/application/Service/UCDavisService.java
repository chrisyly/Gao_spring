import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.opencsv.CSVReader;

/**
 * 
 * @author Mushi
 * Aug.19th
 */
public class UCDavisService {
	
	private Map<String, String> mapOfRequest;
	
	public static Map<String, Map<String, String>> data = new HashMap<>();

	public JSONObject temp;
	
	public UCDavisService() {}
	
	// read from local CSV, TODO
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
	public Map<String, Map<String, String>> getDataMapFromCSV(String csvPath) {
		Map<String, Map<String, String>> targetAndData = new HashMap<>();
    	Map<String, String> DateAndTemp = new HashMap<>();
    	
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        boolean isFirstRow = true;
        // initialize the item index
        int indexOfDate = 0;
		int indexOfHour = 0;
		int indexOfTemp = 0;
		int indexOfHlyTemp = 0;

        try {
            br = new BufferedReader(new FileReader(csvPath));
            while ((line = br.readLine()) != null) {
            	String[] items = line.split(cvsSplitBy);
            	if (isFirstRow) {
            		 indexOfDate = Arrays.asList(items).indexOf("Date");
            		 indexOfHour = Arrays.asList(items).indexOf("Hour");
            		 indexOfTemp = Arrays.asList(items).indexOf("DayAirTmpAvg");
            		 indexOfHlyTemp = Arrays.asList(items).indexOf("HlyAirTmp");
            		 isFirstRow = false;
            		 continue;
            	}
            	// matching data and temperature
            	if (indexOfHour == -1) { // daily
					DateAndTemp.put(items[indexOfDate], items[indexOfTemp]);
				} else { // hourly
					DateAndTemp.put(items[indexOfDate] + ":" +items[indexOfHour] , items[indexOfHlyTemp]);
				}
            }
            targetAndData.put(new File(csvPath).getName(), DateAndTemp);
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
        return targetAndData;
	}
	
	// TODO, database
	public void updateData() {
		
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
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File(fileName+".csv"));
			pw.write(csv.toString());
	        pw.close();
	        System.out.println("csv file: <" + fileName + "> created");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String calculateHeatUnitResult(String key) {
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
		
		// check the method TODO
		if (request.contains("method")) {
			keyWord = "method";
			// do nothing
		}
		
		return queryMap;
	}
}
