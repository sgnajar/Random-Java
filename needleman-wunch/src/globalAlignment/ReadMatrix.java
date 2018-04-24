package globalAlignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReadMatrix {
	//----- Members
	private int theDimension;
	private char[] myAminioAcidArray;
	private int[][] myNewMatrix;

	//---- Constructors
	public ReadMatrix(int dimension, int[][] matrix, char[] mirroredLabelArray)
	{
		this.theDimension = dimension; // number of row = column (square matrix)
		this.myAminioAcidArray = mirroredLabelArray;
		
		int [][] tempData = new int [theDimension][theDimension];
		
		for (int i = 0; i < theDimension; i++)
		{
			for (int j = 0; j < theDimension; j++)
			{
				tempData[i][j] = matrix[i][j];
			}
		}
		this.myNewMatrix = tempData;
	}
	
	//----- Reading the Scoring Matrix
	
	@SuppressWarnings("resource")
	public static ReadMatrix readScoringMatrix (File inFileMatrix) throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(inFileMatrix));
		String line;
		int numLine = 0;

		List<Character> myAAList = new LinkedList<Character>();		
		List <int[]> tempMatrixArrayList = new ArrayList <int[]>();
		
		try 
		{
			br = new BufferedReader(new FileReader(inFileMatrix));
			
//			if ((line = br.readLine()) == null)
//				throw new IOException("this file is empty");
				//System.out.println("this file is empty");
				while ((line = br.readLine()) != null)
				{ 
					String[] lineArray = line.split("\t");
					//System.out.println(numLine);
					if (numLine == 0)
					{
						for (int x = 1; x < lineArray.length; x++)
						{
							myAAList.add(lineArray[x].charAt(0));
						}
					}
					else
					{
						int[] theScores = new int [lineArray.length -1];
						for (int x = 1; x < lineArray.length; x++)
						{
							theScores[(x-1)] =  Integer.parseInt(lineArray[x]);
						}
						tempMatrixArrayList.add(theScores);

					}
					numLine++;
//					else if (numLine >= 2)
//					{
//						String[] myScores = line.split(splitBy);
//						for (int x = 0; x < theDimention; x++)
//						{
//							myNewMatrix[numLine-2][x] = Integer.parseInt(myScores[x+1]);
//						}
//					}

				}
				br.close();
				
		//System.out.println("I am printing the matrix:");
		//getMatrixToDisplay(myNewMatrix);
		
		}catch(FileNotFoundException e){
			System.out.println("Unable to find the file");
			
		}catch (IOException e){
			System.out.println("Unable to read the file");
		}
		//System.out.println(tempMatrixArrayList.size());
		int scoreMatrixDimension = tempMatrixArrayList.get(0).length;
		//System.out.println(scoreMatrixDimension);
		int[][] myScoreMatrix = new int[scoreMatrixDimension][scoreMatrixDimension];
		for (int i = 0; i < scoreMatrixDimension; i++)
		{
			int[] rowArray = tempMatrixArrayList.get(i);
			//System.out.println(rowArray.length);
			for (int j = 0; j < scoreMatrixDimension; j++)
			{
				myScoreMatrix[i][j] = rowArray[j];
			}
		}
		
		char[] mirroredLabelArray = new char[scoreMatrixDimension];
		for (int x=0; x < scoreMatrixDimension; x++)
		{
			mirroredLabelArray[x]= myAAList.get(x);
		}
		
		ReadMatrix blosumMatrix = new ReadMatrix (scoreMatrixDimension, myScoreMatrix, mirroredLabelArray);
		return blosumMatrix;
	}
	
//	public static int getDimension(File inFileMatrix2){
//		int dim = 0;
//		try 
//		{
//			BufferedReader br = new BufferedReader(new FileReader(inFileMatrix2));
//			while (br.readLine() != null)
//			{ 
//				dim++;
//			}
//			System.out.println("The dimension of this matrix is: "+ dim);
//			br.close();
//		}catch(FileNotFoundException e){
//			System.out.println("Unable to find the file");
//		}catch (IOException e) {
//			System.out.println("Unable to read the file");
//			e.printStackTrace();
//		}
//		
//		return dim-1;
//	}
	
	public int getDimension()
	{
		return theDimension;
	}
	
	public int[][] getScoringMatrix()
	{
		return myNewMatrix;
	}
	
	public char[] getAminoAcidArray()
	{
		return myAminioAcidArray;
	}
	
	public void getMatrixToDisplay(){
		int[][] x = this.myNewMatrix;
		for (int row = 0; row < x.length; row++){
			for (int col = 0; col < x[row].length; col++){
				System.out.print(x[row][col]+ " ");
			}
			System.out.println();
		}
	}
	
//	public static void main(String[] args) throws Exception 
//	{
//		File inFileMatrix = new File ("C:\\Users\\Sasan Najar\\Desktop\\BLOSUM50t.txt");
//		readMatrix myMatrix = readMatrix.readScoringMatrix(inFileMatrix);
//
//		for (readMatrix rM: myMatrix)
//		{
//			System.out.println(rM.getDimension());
//		}
//		
//		int [][] scoreMatrix = myMatrix.getScoringMatrix();
//		System.out.println(scoreMatrix);
//		
//	}
}
