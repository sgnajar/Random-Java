package globalAlignment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ReadSeq {
	
	//---- Member
	private String header;
	private StringBuffer sequence = new StringBuffer();
	
	//---- Constructors
//	public ReadSeq(String header, String sequence)
//	{
//		this.header = header;
//		this.sequence = sequence; 
//	}

	//---- Reading the file
	public static List<ReadSeq> readSeqFile (File inFileTwoSeqs) throws Exception
	{
		List<ReadSeq> myList = new ArrayList <ReadSeq>();
		try 
		{
			BufferedReader br = new BufferedReader (new FileReader(inFileTwoSeqs));
			
			String line = br.readLine();
			ReadSeq text = null;
			
			if (line == null)
				throw new IOException ("The file " + inFileTwoSeqs + " is empty.");
			else if (line.charAt(0) != '>')
				throw new IOException ("The file " + inFileTwoSeqs + " is not the correct format. Try again!");
			while (line != null)
			{
				if (line.length() > 0 && line.charAt(0) == '>')
				{
					text = new ReadSeq();
					myList.add(text);
					text.header = line;
				}
				if (line.length() > 0 && line.charAt(0) != '>')
				{
					text.sequence.append(line);
				}
				line = br.readLine();
			}
			br.close();
		}catch(FileNotFoundException e){
			System.out.println("Unable to find the file " + inFileTwoSeqs);
			
		}catch (IOException e){
			System.out.println("Unable to read the file" + inFileTwoSeqs);
		}
		return myList;
	}
	
	// -- method to get the header
	public String getHeader()
	{
		//return this.header;
		return this.header.substring(1);
	}
	
	public String getSequence()
	{
		return this.sequence.toString();
	}
	
//	public static void main(String[] args) throws Exception 
//	{
//		String inFileTwoSeqs = "C:\\Users\\Sasan Najar\\Desktop\\twoSeqs.txt";
//		File inFile = new File(inFileTwoSeqs);
//		List<readSeq> tempList = readSeq.readSeqFile(inFile);
	

//		System.out.println("The number of sequences in this file is " + tempList.size());
//		System.out.println("\nThe header of the sequences:");
//
//		for (readSeq rS: tempList)
//		{
//			System.out.println(rS.getHeader());
//		}
//		
//		System.out.println("\nThe sequence of the file:");
//
//		for (readSeq rS: tempList)
//		{
//			String seq = rS.getSequence();
//			System.out.println(seq);
//		}
//	}
}
