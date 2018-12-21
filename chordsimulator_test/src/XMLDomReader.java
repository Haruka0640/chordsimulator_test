
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLDomReader {

	public static Document buildDocument(String file){
	    try {
	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        Document document = docBuilder.parse(file);
	        return document;
	    } catch (ParserConfigurationException | SAXException | IOException ex) {
	        System.out.println("the exception is: " + ex.toString());
	    }
	    return null;
	}
}
