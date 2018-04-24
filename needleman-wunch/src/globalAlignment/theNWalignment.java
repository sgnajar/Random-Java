package globalAlignment;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Step 1 - Get the sequence
 * Step 2 - Get the scoring matrix
 * Step 3 - Get Penalty gap
 * Step 4 - alignment
 * Step 5 - Trace-back
 */

public class theNWalignment {
	public static String mySequence1;
	public static String mySequence2;
	public static int[][] myScoreMatrix;
	public static Map<Character, Integer> myAminoLabels = new HashMap<Character, Integer>();
	private static int theGapPenalty;
	
	public static void main(String[] args) throws Exception {
		
		//Step 1 - Get the sequence
		File inFileTwoSeqs = new File("C:\\Users\\Sasan Najar\\Desktop\\twoSeqs.txt");
		List<ReadSeq> mySequence = ReadSeq.readSeqFile(inFileTwoSeqs);
		mySequence1 = mySequence.get(0).getSequence();
		mySequence2 = mySequence.get(1).getSequence();
		
//		mySequence1 = "GGGGGGGGGGGGGG";
//		mySequence2 = "HHHHGGHH";
		
		//Step 2 - Get the scoring matrix
		File inFileMatrix = new File ("C:\\Users\\Sasan Najar\\Desktop\\BLOSUM50t.txt");		
		ReadMatrix myMatrix = ReadMatrix.readScoringMatrix(inFileMatrix);
		myScoreMatrix = myMatrix.getScoringMatrix();
		//myMatrix.getMatrixToDisplay();

		int indexCount = 0;
		char[] myAminoArrayLabels = myMatrix.getAminoAcidArray();
		for (int x = 0; x < myAminoArrayLabels.length; x++)
		{
			myAminoLabels.put(myAminoArrayLabels[x], indexCount);
			indexCount++;
		}
	
		// Step 3 - Get Penalty gap
		theGapPenalty = -8;
		
		// Step 4 - alignment
		char[] a1 = mySequence1.toCharArray();
		char[] a2 = mySequence2.toCharArray();
		
		//System.out.println(a2);
		
		int numRow = mySequence1.length()+1;
		int numCol = mySequence2.length()+1;
		
		int[][] alignMatrix = new int[numRow][numCol];
		// traceback matrix; 0=stop, 1=diag, 2=up, 3=left
		int[][] tracebackMatrix = new int[numRow][numCol];
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < numRow; i++)
		{
			for (int j = 0; j < numCol; j++)
			{
				// Start with 0
				if (i == 0 && j == 0)
				{
					alignMatrix[i][j] = 0;
					tracebackMatrix[i][j] = 0;
				}
				
				// horz - adding gap penalty
				else if (i == 0 && j != 0)
				{
					alignMatrix[i][j] = alignMatrix[i][j-1] + theGapPenalty;
					tracebackMatrix[i][j] = 3;
				}
				
				// vert - adding gap penalty
				else if (i != 0 && j == 0)
				{
					alignMatrix[i][j] = alignMatrix[i-1][j] + theGapPenalty;
					tracebackMatrix[i][j] = 2;
				}
				
				// diag - Match or Mismatch
				else
				{
					// Compute diag vert horz and select max and depending on which max selected, fill traceback with correct
					char charRow = a1[i-1];
					char charCol = a2[j-1];
					
					int rowIndex = myAminoLabels.get(charRow);
					int colIndex = myAminoLabels.get(charCol);
//					System.out.println(rowIndex);
//					System.out.println(colIndex);
					
					int diagonalScore = alignMatrix[i-1][j-1] + myScoreMatrix[rowIndex][colIndex];
					int verticalScore = alignMatrix[i-1][j] + theGapPenalty;
					int horizontalScore = alignMatrix[i][j-1] + theGapPenalty;
					
					int maxScore = Math.max(diagonalScore, Math.max(verticalScore, horizontalScore));
					alignMatrix[i][j] = maxScore;
					
					if (maxScore == diagonalScore)
					{
						tracebackMatrix[i][j] = 1;
					}
					else if (maxScore == verticalScore)
					{
						tracebackMatrix[i][j] = 2;
					}
					else
					{
						tracebackMatrix[i][j] = 3;
					}
				}
			}
		}

		// Step 5 - Trace-back
		
		int numRowTraceback = tracebackMatrix.length-1;
		int numColTraceback = tracebackMatrix[0].length-1;
//		System.out.println(mySequence1.length());
//		System.out.println(numRowTraceback);
		String theFinalUpSeq = "";
		String theFinalSideSeq = "";
		
		while (true)
		{
//			System.out.println(numRowTraceback);
//			System.out.println(numColTraceback);
			int myTrace = tracebackMatrix[numRowTraceback][numColTraceback];
			
			if (myTrace == 3)
			{
				char myUpChar = mySequence2.charAt(numColTraceback-1);
				numColTraceback = numColTraceback - 1;
				theFinalSideSeq = theFinalSideSeq + '-';
				theFinalUpSeq = theFinalUpSeq + myUpChar;
			}
			else if (myTrace == 2)
			{
				char mySideChar = mySequence1.charAt(numRowTraceback-1);
				numRowTraceback = numRowTraceback - 1;
				theFinalSideSeq = theFinalSideSeq + mySideChar;
				theFinalUpSeq = theFinalUpSeq + '-';
			}
			else if (myTrace == 1)
			{
				char myUpChar = mySequence2.charAt(numColTraceback-1);
				char mySideChar = mySequence1.charAt(numRowTraceback-1);
				numRowTraceback = numRowTraceback - 1;
				numColTraceback = numColTraceback - 1;
				theFinalSideSeq = theFinalSideSeq + mySideChar;
				theFinalUpSeq = theFinalUpSeq + myUpChar;
			}
			else if (myTrace == 0)
			{
				break;
			}
		}
		
//		for (int i = numRowTraceBack; i> -1; i--)
//		{
//			for (int j = numColTraceBack; j> -1; j--)
//			{
//				int myTrace = tracebackMatrix[i][j];
//				
//			}
//		}
		
		String theFinalUpSeqReverse = new StringBuilder(theFinalUpSeq).reverse().toString();
		String theFinalSideSeqReverse = new StringBuilder(theFinalSideSeq).reverse().toString();

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;

		System.out.println(theFinalUpSeqReverse);
		System.out.println(theFinalSideSeqReverse);
		
		System.out.println("\nTotal taken: " + duration + " ms.");
	}
}
