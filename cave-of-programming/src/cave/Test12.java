package cave;

public class Test12 {
	public static void main(String[] args) 
	{
		int[][] test = { { 11, 22, 33 }, { 44, 55, 66 }, { 77, 88, 99 } };
		//System.out.println(test[1][1]);
		for (int row = 0; row < test.length; row++) {
			for (int col = 0; col < test[row].length; col++) {
				System.out.print(test[row][col] + "\t");

			}
			System.out.println();
		}
	}
}
