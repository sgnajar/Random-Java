
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class writeUnique implements Comparable<writeUnique> {

	// ------------- member
	private String header;
	private StringBuffer sequence = new StringBuffer();
	List seqList = new ArrayList();

	// ---- ReadFile
	public static List<writeUnique> readFastaFile(File inFile, File outFile) throws Exception
	// public static void writeUnique(File inFile, File outFile) throws
	// Exception
	{
		List<writeUnique> myList = new ArrayList<writeUnique>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			String line = br.readLine();
			writeUnique text = null;

			if (line == null)
				throw new IOException("The file " + inFile + " is empty.");
			else if (line.charAt(0) != '>')
				throw new IOException("The file " + inFile + " is not the correct file format.");

			while (line != null) {
				if (line.length() > 0 && line.charAt(0) == '>') {

					text = new writeUnique();
					myList.add(text);

					text.header = line;

				}
				if (line.length() > 0 && line.charAt(0) != '>')

					text.sequence.append(line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to find the file " + inFile.toString());
		} catch (IOException ex) {
			System.out.println("Unable to read the file " + inFile);
		}
		return myList;
	}

	public String mmm() {
		List<String> list3 = new ArrayList<String>();
		list3.add(getSequence());
		Collections.sort(list3);

		int i = 0;
		for (String temp : list3) {
			return temp;
		}
		return null;
	}

	// --- return the sequence
	public String getSequence() {
		return this.sequence.toString();
	}

	// ---- number of accuracy
	public String getNumber() {
		String str = this.sequence.toString();
		System.out.println("here2 :" + str);
		String[] str2 = str.split("\n");
		Map<String, Integer> myMap = new LinkedHashMap<>();

		for (String a : str2) {
			if (myMap.containsKey(a)) {
				myMap.put(a, myMap.get(a) + 1);
			} else {
				myMap.put(a, 1);
			}
		}
		System.out.println(myMap);
		//////////////////////////
		return str;
	}

	public Map<String, Integer> getSequence2() {
		Map<String, Integer> myMap = new LinkedHashMap<>();

		String seq = this.sequence.toString();

		// myMap.put(this.sequence.toString(), null);
		// for (String a: myMap.keySet())
		// {
		// return myMap.keySet();
		// }
		// System.out.println();
		//
		// return null;
		System.out.println("HERE: " + seq);
		String[] str2 = seq.split("\n");

		for (String a : str2) {
			if (myMap.containsKey(a)) {
				myMap.put(a, myMap.get(a) + 1);
			} else {
				myMap.put(a, 1);
			}
		}
		// System.out.println(myMap);
		return myMap;
	}

	public Collection<String> getNew1() {
		Map<String, Object> myMap = new LinkedHashMap<>();
		for (String a : myMap.keySet()) {
			return myMap.keySet();
		}
		return seqList;
	}

	// ----main method
	public static void main(String[] args) throws Exception {
		// System.out.println("enter the file: ");
		// Scanner inputFile = new Scanner (System.in);
		// File inFile = inputFile.nextLine();
		// List <writeUnique> myList2 = writeUnique.readFastaFile(inFile);

		File inFile = new File("input.txt");
		File outFile = new File("output.txt");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);
		Scanner sc = new Scanner(inFile);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			// System.out.println(line);
		}
		// System.out.println();
		List<writeUnique> myList2 = writeUnique.readFastaFile(inFile, outFile);

		// System.out.println( myList2.size());
		for (writeUnique wU : myList2) {
			// System.out.println(wU.getSequence());
			System.out.println(wU.getSequence2());
			System.out.println(wU.getNumber());
			// System.out.println(wU.mmm());
			// pWriter.print(wU.getSequence2());//(wU.getSequence2());
			// System.out.println(wU.getNew1());
		}
		// System.out.println(wU.getNumber());

	}

	@Override
	public int compareTo(writeUnique o) {
		// TODO Auto-generated method stub
		return 0;
	}

	// one map for all the seq
	// now it is one map for each seq

}
