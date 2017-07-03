import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GetClosestZipcode {
	
	public static void main(String[] args) throws IOException {
		
		// read txt file line by line, transfer the file name to list
		Scanner s = new Scanner(new File("/Users/Mushi/Desktop/Edited_Data/Station_List_with_Zipcode.txt"));
		List<String> stationList = new ArrayList<>();
		while(s.hasNext()){
			stationList.add(s.next());
		}
		s.close();
		
		// transfer to [[xxxxx, xxxx, station1],[ooooo, oooo, station2], ...]
		List<List<Object>> zipcodes = new ArrayList<>();
		for(int i = 0; i < stationList.size(); i++) {
			int length = stationList.get(i).length();
			List<Object> temp = new ArrayList<>();
			temp.add(stationList.get(i).substring(0, 5));
			temp.add(stationList.get(i).substring(6, 10));
			temp.add(stationList.get(i).substring(11, length));
			zipcodes.add(temp);
		}
		System.out.println(".......All Stations......");
		System.out.println(zipcodes.toString());
		
		// input zipcode 9-digit, 例子
		int firstFive = 93211;
		int lastFour = 9730;
		
		// 选出开始五位和目标相差2以内的
		List<List<Object>> inquiry = getClosestZipcodeListBy(firstFive,zipcodes);
		System.out.println(".......Filtered......");
		System.out.println(inquiry.toString());
		
		List<String> result = getClosestStationBy(lastFour, inquiry);
		System.out.println(".......All Stations......");
		System.out.println("Target: "+ firstFive + "-" + lastFour);
		System.out.println(result.toString());
		
	}
	
	private static List<String> getClosestStationBy(int lastFour, List<List<Object>> zipcodes) {
		int[] temp = new int[2];
		temp[1] = Integer.MAX_VALUE;
		
		for (List<Object> zipcode : zipcodes) {
			int last4Zip = Integer.valueOf((String) zipcode.get(1));
			if (last4Zip == lastFour) { return resultBuilder(zipcode);} // hit the target
			else if (Math.abs(last4Zip - lastFour) < temp[1]) {
					temp[1] = Math.abs(last4Zip - lastFour);
					temp[0] = zipcodes.indexOf(zipcode);
				} else {
					continue;
				}
		}
		
		return resultBuilder(zipcodes.get(temp[0]));
	}
	
	private static List<String> resultBuilder(List<Object> zipcode) {
		List<String> rst = new ArrayList<>();
		String s1 = zipcode.get(0) + "-" + zipcode.get(1);
		String s2 = (String) zipcode.get(2);
		rst.add(s1);
		rst.add(s2);
		return rst;
	}

	private static List<List<Object>> getClosestZipcodeListBy(int firstFive, List<List<Object>> zipcodeList) {
		List<List<Object>> result = new ArrayList<>();
		
		for (List<Object> element : zipcodeList) {
			// diff between target and object less than 2
			if (Math.abs(Integer.valueOf((String)element.get(0)) - firstFive) > 2) {
				continue;
			} else {
				result.add(element);
			}
		}
		
		return result;
	}
}
