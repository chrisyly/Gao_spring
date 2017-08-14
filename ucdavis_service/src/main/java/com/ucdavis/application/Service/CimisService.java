package com.ucdavis.application.Service;
import com.ucdavis.application.AppPropert;
import java.io.BufferedReader;
import java.lang.StringBuilder;

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
	private AppProperties appProperty; 
	private String appKey;

	public CimisService() {
		/// Set appKey
		// TODO this.appKey = new AppProperties().getApi().getKey();
	}

	/// return the singleton cimisService
	public static CimisService getService() {
		return cimisService;
	}

	public JSONObject processRequestQuery(String query) {
		// send url query
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(query);
			bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder jsonStringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonStringBuilder.append(line);
			}
			System.out.println(jsonStringBuilder.toString());
			JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());
		} catch (IOException e) {
			System.err.println("Failed to fetch URL: " + query + "\n" + e.printStackTrace());
		} finally {
			if (reader != null) {
				bufferReader.close();
			}
		}
		return jsonObject;
	}
}
