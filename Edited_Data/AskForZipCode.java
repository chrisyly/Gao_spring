import java.util.Scanner;

public class AskForZipCode {
	 public static void main(String[] args){
		 Scanner s = new Scanner(System.in);
		 System.out.print("Lat 1st Number: ");
	     int latFirstNumber = s.nextInt();
	     System.out.print("Lat 2nd number: ");
	     int latSecondNumber = s.nextInt();
	     double lat = latFirstNumber + latSecondNumber/60.0;
	     

	     System.out.print("Lat 1st Number: ");
	     int longFirstNumber = s.nextInt();
	     System.out.print("Lat 2nd number: ");
	     int longSecondNumber = s.nextInt();
	     double longtitude = 0 - longFirstNumber - longSecondNumber/60.0;
	     
	     System.out.println(lat + "," + longtitude);
	     
	 }
}
