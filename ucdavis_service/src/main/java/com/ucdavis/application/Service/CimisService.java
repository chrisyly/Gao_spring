
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.net.MalformedURLException;
import java.net.URL;

/// import json dependency
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/*
 * Author: Liyu Ying @ lying0401@gmail.com
 * Date: 08/13/2017
 * Cimis service, operating cimis weather data in either JSON or XML
 * Generate | Updating local cimis weather data periodly
 * @ref: http://et.water.ca.gov/Rest/Index
 * @parm appKey: registered CIMIS APP KEY for services access
 * @parm requestQuery: String query for CIMIS data
 */

public class CimisService {
	/// singleton reference
	private static final CimisService cimisService = new CimisService();
	private String appKey;
	
	
	private static String[] allDefaultKeys = {"Date","julian", "station", "standard", "zipcodes", "scope", "day-air-tmp-avg", "day-air-temp-max", "day-air-tmp-min","day-asce-eto", "day-sol-rad-avg", "day-wind-spd-avg"};
	private static String[] stNbrDailyKeys = {"Date","julian", "station", "standard", "zipcodes", "scope", "day-air-tmp-avg", "day-air-temp-max", "day-air-tmp-min"};
	private static String[] stNbrDailyAvgKeys = {"date", "julian", "station", "standard", "zipcodes", "scope", "day-air-tmp-avg"};
	private static String[] stNbrHourlyKeys = {"date", "julian", "station", "standard", "zipcodes", "scope", "day-air-tmp-avg", "hour"};
	private static String[] zipcodeDailyKeys = {"date", "julian", "standard", "zipCodes", "scope", "day-asce-eto", "day-sol-rad-avg", "day-wind-spd-avg"};
	private static String[] zipcodeDailyAvgKeys = {"date", "julian", "station", "standard", "zipcodes", "scope", "day-air-tmp-avg"};
	private static String[] zipcodeHourlyKeys = {"date", "julian", "station", "standard", "zipcodes", "scope", "day-air-tmp-avg", "hour"};

	public CimisService() {
		/// Set appKey
		// TODO this.appKey = new AppProperties().getApi().getKey();
	}

	/// return the singleton cimisService
	public static CimisService getService() {
		return cimisService;
	}
	
	/*
	 * read String url query and return JSONObject
	 */
    public JSONObject processRequestQuery(String query) {
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(query);
			bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder jsonStringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonStringBuilder.append(line);
			}
			//System.out.println(jsonStringBuilder.toString());
			JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());
			// TODO update data using jsonObject
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
    }
    
    /*
     * write JSONArray to CSV
     */
    public void writeToCsv(JSONArray jsonArray) {
    	
    }
}
