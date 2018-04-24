import java.io.File;
import java.io.IOException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class createXMLfile {
	static Document document;

	public static void main(String[] args) {
		if ( argv.length != 1 ) {
			System.err.println("Usage: java TransformationApp01 filename");
			System.exit(1);
		}
		DocumentBuilderFactory factory =
		        DocumentBuilderFactory.newInstance();

		        try {
		            File f = new File(argv[0]);
		            DocumentBuilder builder =
		            factory.newDocumentBuilder();
		            document = (Document) builder.parse(f);
		  
		        } 
		        catch (SAXParseException spe) {
		            // Error generated by the parser
		            System.out.println("\n** Parsing error"
		                               + ", line " + spe.getLineNumber()
		                               + ", uri " + spe.getSystemId());
		            System.out.println("  " + spe.getMessage() );
		  
		            // Use the contained exception, if any
		            Exception x = spe;
		            if (spe.getException() != null)
		                x = spe.getException();
		            x.printStackTrace();
		        }
		        catch (SAXException sxe) {
		            // Error generated by this application
		            // (or a parser-initialization error)
		            Exception x = sxe;
		            if (sxe.getException() != null)
		                x = sxe.getException();
		            x.printStackTrace();
		        }
		        catch (ParserConfigurationException pce) {
		            // Parser with specified options 
		            // cannot be built
		            pce.printStackTrace();
		        }
		        catch (IOException ioe) {
		            // I/O error
		            ioe.printStackTrace();
		        }
	}

}

