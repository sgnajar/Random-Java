package cave;

public class Test11 
{

	public static void main(String[] args) 
	{
		int[] values = { 3, 5, 7 };
		System.out.println(values[2]);
		
		int [][]grid = {
				{1,2,3},
				{4,5,6},
				{7,8,9}};
		System.out.println(grid[0][2]);
		System.out.println(grid[1][1]);
		
		String[][]tests = new String[2][3];
		System.out.println(tests[0][2]);
		
		for (int row=0; row<grid.length; row++){
			for (int col = 0; col<grid[row].length;col++){
				System.out.print(grid[row][col] + "\t");
			}
			System.out.println();
		}
		
	}

}
