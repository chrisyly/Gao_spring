import java.text.ParseException;

public class HeatUnitServicedemo {
	public static void main(String [] args) throws ParseException {
		HeatUnitService fooservice = new HeatUnitService();
		
		// 1/7 --> 3/4
		fooservice.setBloomDate("20170107");
		fooservice.setCurrentDate("20170304");
		
		fooservice.setDataList();
		System.out.println(fooservice.getDataList());
		
		fooservice.setHeatUnit();
		System.out.println(fooservice.getHeatUnit());
		
		fooservice.setSumOfHeatUnit();
		System.out.println(fooservice.getSumOfHeatUnit());
		
		fooservice.setMaxHeatUnit(2050.00);
		System.out.println(fooservice.getMaxHeatUnit());
		fooservice.setPrediction();
		System.out.println(fooservice.getPrediction());
		System.out.println(fooservice.toString());
	}
}