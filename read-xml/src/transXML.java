import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.text.Document;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

public class transXML {
	static Document document;
	BufferedReader myInput;
	StreamResult myOutput;
	TransformerHandler th;
	
	public void startUp(){
		
		try {
			myInput = new BufferedReader(new FileReader("C:\\Users\\Sasan Najar\\Desktop\\someRdOut.txt"));
			myOutput = new StreamResult("C:\\Users\\Sasan Najar\\Desktop\\myData.xml"); //should be C:\\Users\\Sasan\\git\\Prefuse\\data\\myData.aml
			openXml();
			String str;
			while((str = myInput.readLine())!= null){
				process(str);
			}
			myInput.close();
			closeXml();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	  public void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

	        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	        th = tf.newTransformerHandler();

	        // pretty XML output
	        Transformer serializer = th.getTransformer();
	        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        serializer.setOutputProperty(OutputKeys.INDENT, "yes");

	        th.setResult(myOutput);
	        th.startDocument();
	        th.startElement(null, null, "!-- Here is the start point--", null);
	    }

	    public void process(String s) throws SAXException {
	        th.startElement(null, null, "data", null);
	        th.characters(s.toCharArray(), 0, s.length());
	        th.endElement(null, null, "data");
	    }

	    public void closeXml() throws SAXException {
	        th.endElement(null, null, "graph\n");
	        th.endElement(null, null ,"graphml");
	        th.endDocument();
	    }
   
	

public static void main(String[] args) {
//	if (argv.length !=1){
//		System.err.println("Usage: java transXML filename");
//		System.exit(1);
//	}
	new transXML().startUp();
}
}

