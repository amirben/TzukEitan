package TzukEitan.view;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import TzukEitan.war.War;
import TzukEitan.war.WarControl;

public class TzukEitan {

	public static void main(String[] args) {
		ConsoleView view = new ConsoleView();
		War warModel = new War();
		
		WarControl warControl = new WarControl(warModel, view);
		warModel.start();
		
		try {
			WarXMLReader warXML = new WarXMLReader("warStart.xml", warControl);
			warXML.start();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
		

	}

}
