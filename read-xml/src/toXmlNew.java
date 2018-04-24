import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.XMLReader;

public class toXmlNew {
	// member
	private StringBuffer myLines = new StringBuffer();
	//private String myLines;
	//private String reader;
	// Reading the file
	public static List <toXmlNew> readSpreadSheet(File inFile){
		List <toXmlNew> myLevelList = new ArrayList<toXmlNew>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			String line = br.readLine();
			toXmlNew myText = null;
			if (line == null)
				throw new IOException("this file is empty");
		
			while ((line = br.readLine()) != null)
			{
					myText = new toXmlNew();
					myLevelList.add(myText);
					myText.myLines.append(line);
			}

			br.close();

		}catch (FileNotFoundException e){
			System.out.println("unable to find the file");
		}catch (IOException e){
			System.out.println("unable to read the file");
		}
		//System.out.println(myList);
		return myLevelList;
	}
			
	public String getMyLines(){
		return this.myLines.toString();
	}
			
	// main method		
	public static void main(String[] args) {
		File inFile = new File("C:\\Users\\Sasan\\Desktop\\someRdOut.txt");
		//inFile = new File (inFile.n)
		List<toXmlNew> myTempList = readSpreadSheet (inFile);
		for (toXmlNew xN: myTempList){
			System.out.println(xN.myLines);
		}
	}

}
