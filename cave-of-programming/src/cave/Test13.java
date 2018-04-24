package cave;

class car {
	String make;
	String model;
	int year;
	
	void cars() {
		System.out.println("make:" + make + ", model:" + model + ", year:" + year);
	}
}

public class Test13 {

	public static void main(String[] args) {
		car car1 = new car();
		car1.make = "honda";
		car1.model = "civic";
		car1.year = 2015;
		car1.cars();

		car car2 = new car();
		car2.make = "toyota";
		car2.model = "corolla";
		car2.year = 2012;
		car2.cars();

		
	}

}
