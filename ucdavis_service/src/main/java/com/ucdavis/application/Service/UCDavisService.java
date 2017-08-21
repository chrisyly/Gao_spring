import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	
	private Map<String, String> mapOfRequest;
	
	public static Map<String, Map<String, String>> data = new HashMap<>();

	public JSONObject temp;
	
	public UCDavisService() {}
	
	// read from local CSV, TODO
	public UCDavisService(String todo) {
		
	}
	
	// save data into local Map:data
	public void getDataFromList(List<JSONObject> records) {
		// traverse the list
		Map<String, String> recordMap = new HashMap<>();
		for (JSONObject record : records) {
			// find the key of recordMap, date & hour
			String dateValue = record.get("Date");
		}
		UCDavisService.data.put(this.mapOfRequest.get("targets"), recordMap);
	}
	
	// local CSV
	public void getDataFromCSV(String csvPath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvPath));
			String line = null;
			String cvsSplitBy = ",";
			
			
		} catch (FileNotFoundException e) {
			System.err.println("[Fail]Fail to find the file");
			e.printStackTrace();
		}
		
		
	}
	
	// TODO, database
	public void updateData() {
		
	}
	
	/**
	 * write List<JSONObject> to CSV, file name is the key of map
	 */
	public void writeToCSV(List<JSONObject> records, String fileName) {
		File file = new File("/" + fileName +".csv");
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
			System.out.println(i);
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
