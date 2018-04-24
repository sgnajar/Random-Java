package cave;

public class Test9 {

	public static void main(String[] args) {
		//int value = 7;
		int [] values;
		values = new int [5];
		
		values [0] = 10;
		values [1] = 15;
		values [2] = 20;
		values [3] = 350;
		values [4] = 3330;
//		System.out.println(values[0]);
//		System.out.println(values[1]);
//		System.out.println(values[2]);
//		System.out.println(values[3]);
//		System.out.println(values[4]);
		
		for ( int i=0; i < values.length; i++)
		{
			System.out.println(values[i]);
		}
		
		int [] numbers = {3, 5, 6, 7};
		
		for (int i=0; i<numbers.length; i++)
		{
			System.out.println(numbers[i]);
		}
	}
}
