import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Mushi
 * Aug.19th, 2017
 */
public class UCDavisService {

	private List<JSONObject> records;
	private List<String> dates;
	private List<Double> temps;
	private List<String> zipcodes;
	private List<Double> heatUnits;
	private String scope;

	// constructor
	public UCDavisService() {
		super();
	}
	
	// constructor
	public UCDavisService(List<JSONObject> records) {
		this.records = records;
	}
	
	// set all data
	public void setAllResponse() {
		setDates();
		setTemps();
		setZipcodes();
		setHeatUnits();
	}

	// getter and setter
	public List<JSONObject> getRecords() {
		return records;
	}

	public void setRecords(List<JSONObject> records) {
		this.records = records;
	}

	// getter and setter, dates
	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public void setDates() {
		this.dates = readFromRecords("Date", this.records);
	}
	
	// getter and setter, temps
	public List<Double> getTemps() {
		return temps;
	}

	public void setTemps(List<Double> temps) {
		this.temps = temps;
	}
	
	public void setTemps() {
		List<String> tempsString = readFromRecords("DayAirTmpAvg", this.records);
		this.temps = extractTempData(tempsString);
	}

	// getter and setter, zipcodes
	public List<String> getZipcodes() {
		return zipcodes;
	}

	public void setZipcodes(List<String> zipcodes) {
		this.zipcodes = zipcodes;
	}
	
	public void setZipcodes() {
		this.zipcodes = readFromRecords("ZipCodes", this.records);
	}

	// getter and setter, Scope
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public void setScope() {
		this.scope = readFromRecords("Scope", this.records).get(0);
	}

	//getter and setter, heat units
	public List<Double> getHeatUnits() {
		return heatUnits;
	}

	public void setHeatUnits(List<Double> heatUnits) {
		this.heatUnits = heatUnits;
	}
	
	public void setHeatUnits() {
		this.heatUnits = getHeatUnitList(this.temps);
	}
	// functions
	/**
	 * read the list of JSONObject, and return List of value to the specific key
	 * @param key
	 * @return list of specific data
	 */
	public List<String> readFromRecords(String key, List<JSONObject> records) {
		List<String> rst = new ArrayList<>();
		//traverse records
		for (JSONObject record : records) {
			try {
				rst.add(record.get(key).toString());
			} catch (JSONException e) {
				System.err.println("[Fail] Fail to read JSONObject with key: <" + key + ">");
				e.printStackTrace();
			}
		}
		return rst;
	}

	/**
	 * parse List<String> temperature data to List<Double>
	 * @param tempSet
	 * @return list of Double, temperature(F)
	 */
	public List<Double> extractTempData(List<String> tempSet) {
		List<Double> extracted = new ArrayList<>();
		//traverse tempSet
		for (String temp : tempSet) {
			extracted.add(Double.parseDouble(temp));
		}
		return extracted;
	}
	
	/**
	 * transfer Fahrenheit to Celsius, and calculate the heat unit
	 * @param FTemps
	 * @return list of Heat Units
	 */
	public List<Double> getHeatUnitList(List<Double> FTemps) {
		List<Double> heatUnits = new ArrayList<>();
		for (Double FTemp : FTemps) {
			Double CTemp = (FTemp - 32) / 1.8 - 7;
			heatUnits.add(CTemp);
		}
		return heatUnits;
	}
	
	/**
	 * return sum of Heat Units by list of Heat Units
	 * @param heatUnits
	 * @return sum of heat units
	 */
	public Double sumOfHeatUnits(List<Double> heatUnits) {
		Double sum = (double) 0;
		for (Double heatUnit : heatUnits) { sum += heatUnit;}
		return sum;
	}
	
	/**
	 * using StringBuilder to build up the result
	 * @return result type
	 */
	public String typeResult() {
		StringBuilder builder = new StringBuilder("Result:");
		// add period description
		builder.append(" from <" + this.getDates().get(0) + "> to <" + this.getDates().get(this.getDates().size() - 1)+ ">");
		// add heat units
		builder.append("\n");
		builder.append("Heat Units: <");
		builder.append(this.getHeatUnits().toString() + ">");
		// add heat units
		builder.append("\n");
		builder.append("Total Heat Units: <");
		builder.append(sumOfHeatUnits(this.getHeatUnits()).toString() + ">");
		
		return builder.toString();
	}
}
