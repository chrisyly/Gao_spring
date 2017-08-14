public class demo3 {

	
	public static void main(String[] args) throws Exception{
	
		// dummy data
		String bloomDate = "2017-02-01";
		String currentDate = "2017-07-01";
		String station = "93210-9656-FIVE_PTS.A";
		
		//DataRequest
		DateRequest request = new DateRequest();
		request.setBloomDate(bloomDate);
		request.setCurrentDate(currentDate);
		request.setStation(station);
		System.out.println(request.toString());
		
		// AppProperties
		String appKey = "4a3b5300-f248-4b6a-bb49-8368d1cac187";
		AppProperties properties = new AppProperties();
		properties.getApi().setKey(appKey);
		System.out.println(properties.getApi().getKey());
		
		// Demo part
		CimisAPIService service = new CimisAPIService(request, properties);
		service.setMaxHeatUnit(2050.0);
		service.setResponse();
		service.setSumOfHeatUnit();
		System.out.println(service.getHeatUnits());
		System.out.println(service.getSumOfHeatUnit());
		System.out.println(service.getHeatUnits().size());
	}
}
