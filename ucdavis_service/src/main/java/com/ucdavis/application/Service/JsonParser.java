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

public class JsonParser {

	// constructor, super
	public JsonParser() {
		super();
	}
	
	/*
	 * read from a string, return the JSON object
	 */
	public JSONObject readFrom(String string) {
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * read from a File, return the JSON object
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
	

	/*
	 * convert a String(JSON) to Map
	 */
	
	public Map<String, String> jsonStringToMap(String jsonString) {
	    JSONObject json;
	    Map<String, String> rstMap = new HashMap<>();
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
	
	/*
	 * return List<JSONObject> from JSONArray
	 */
	public List<JSONObject> getListFromJson(JSONArray jsonArray) {
		return getListFromJson(jsonArray.toString());
	}
}
