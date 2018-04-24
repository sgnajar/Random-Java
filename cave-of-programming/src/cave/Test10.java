package cave;

public class Test10 {

	public static void main(String[] args) {
		String[] words = new String[3];
		words[0] = "hello";
		words[1] = "to";
		words[2] = "you";

		System.out.println(words[2]);

		String[] cars = { "vw", "bmw", "benz", "mazda", "nissan", "honda" };

		for (String car : cars) {
			System.out.println(car);
		}

		String[] temp = new String[3];
		temp[0] = "one";
		System.out.println(temp[0]);
	}
}
