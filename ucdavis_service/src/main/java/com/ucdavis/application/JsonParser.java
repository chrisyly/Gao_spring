package com.ucdavis.application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Mushi
 *
 */
/**
 * @author Mushi
 *
 */
public class JsonParser {

	// constructor, super
	public JsonParser() {
		super();
	}
	
	/**
	 * @param string
	 * @return
	 */
	public JSONObject readFrom(String string) {
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param file
	 * @return
	 */
	@SuppressWarnings("finally")
	public JSONObject readFrom(File file) {
		try {
			Object obj = new JSONParser().parse(new FileReader(file));
			return (JSONObject) obj;
		} catch (FileNotFoundException e) {
			System.err.println("[FAIL] Failed to find JSON file: <"+ file.getName() + ">");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("[FAIL] Failed to read JSON file: <"+ file.getName() + ">");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("[FAIL] Failed to parse from JSON file: <"+ file.getName() + ">");
			e.printStackTrace();
		} finally {
			return null;
		}
	}

	/**
	 * @param jsonString
	 * @return
	 */
	public Map<String, String> jsonStringToMap(String jsonString) {
		Map<String, String> rstMap = new HashMap<>();
		// if jsonString is null, TODO
		if (jsonString == null) return rstMap;
		// not null
	    JSONObject json;
		try {
			json = new JSONObject(jsonString);
			Iterator<?> itr = json.keys();
		    while(itr.hasNext()) {
		    	String key = (String)itr.next();
		    	if (json.get(key) instanceof JSONObject) {
		    		rstMap.put(key, json.get(key).toString());
		    		rstMap.putAll(jsonStringToMap((json.get(key)).toString()));
		    	} else if (json.get(key) instanceof JSONArray) {
		    		rstMap.put(key, json.get(key).toString());
		    		for (int i = 0; i < ((JSONArray) json.get(key)).length(); i++) {
		    			rstMap.putAll(jsonStringToMap(((JSONArray) json.get(key)).get(i).toString()));
		    		}
		    	} else {
		    		rstMap.put(key, json.get(key).toString());
		    	}
		    }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return rstMap;
	}
	
	/*
	 * convert a JSONObject to Map
	 */
	public Map<String, String> jsonStringToMap(JSONObject json) throws JSONException {
    	return jsonStringToMap(json.toString());
	}
	
	/*
	 * toString
	 */
	public String toString(JSONObject jsonObject) {
		return jsonObject.toString();
	}
	
	/*
	 * return List<JSONObject> from String
	 */
	public List<JSONObject> getListFromJson(String inputString) {
		List<JSONObject> rstList = new ArrayList<>();
		// if inputString is null
		if (inputString == null) return rstList;
		// not null
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(inputString);
			for (int i = 0; i < jsonArray.length(); i++) {
				rstList.add((JSONObject) jsonArray.get(i));
			}
		} catch (JSONException e) {
			System.err.println("[FAIL] Failed to read JSONArray from input: <" + inputString +">");
			e.printStackTrace();
			return null;
		}
		
			
		return rstList;
	}
	
	/**
	 * @param jsonArray
	 * @return
	 */
	public List<JSONObject> getListFromJson(JSONArray jsonArray) {
		return getListFromJson(jsonArray.toString());
	}
	
	/*
	 * extract data item from origin JSONArray
	 */
	public JSONArray extractDataItems(JSONArray jsonArray) {
		return extractDataItems(jsonArray.toString());
	}
	
	/*
	 * extract data item from origin JSONArrayString
	 */
	public JSONArray extractDataItems(String inputString) {
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(inputString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = (JSONObject)jsonArray.get(i);
				
				// traverse JSONObject
				Iterator<?> itr = obj.keys();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					// define if value is instance of JSONObject, else do nothing
					if (obj.get(key) instanceof JSONObject) {
						obj.put(key, ((JSONObject)obj.get(key)).get("Value").toString());
					}
				}
				if (obj.has("Hour")) {
					obj.put("Date", obj.getString("Date") + "-" + obj.getString("Hour"));
				}
			}
		} catch (JSONException e) {
			System.err.println("[FAIL] Failed to read JSONArray from input: <" + inputString +">");
			e.printStackTrace();
			return null;
		}
			
		return jsonArray;
	}
	
	public String returnQueryUrl(Map<String, String> queryMapping, String API_KEY) {
		StringBuilder builder = new StringBuilder("http://et.water.ca.gov/api/data?");
		// api key
		builder.append("appKey=" + API_KEY + "&");
		// targets
		builder.append("targets=" + queryMapping.get("targets") + "&");
		// start date
		builder.append("startDate=" + queryMapping.get("start_date") + "&");
		// end date
		builder.append("endDate=" + queryMapping.get("end_date") + "&");
		//daily or hourly, TODO, temporary all add dataItems=day-air-tmp-avg
		builder.append("dataItems=day-air-tmp-avg");
		
		return builder.toString();
	}
	
}
