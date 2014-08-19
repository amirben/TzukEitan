package TzukEitan.view;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.w3c.dom.NamedNodeMap;

import TzukEitan.utils.IdGenerator;
import TzukEitan.war.WarControl;

import javax.xml.parsers.*;

import java.io.*;

public class WarXMLReader extends Thread {
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element root;

	private WarControl warControl;

	public WarXMLReader(String fileName, WarControl warControl) throws ParserConfigurationException,
			SAXException, IOException {
		this.warControl = warControl;
		
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		document = builder.parse(new File(fileName));

		root = document.getDocumentElement();

		
	}
	
	public void run(){
		readEnemyLaunchers();
		readDefenseDestructors();
	}

	protected void readEnemyLaunchers() {
		String idLauncher;
		boolean isHidden;
		Element tempLauncher;

		NodeList launchers = root.getElementsByTagName("launcher");

		for (int i = 0; i < launchers.getLength(); i++) {
			tempLauncher = (Element) launchers.item(i);

			idLauncher = tempLauncher.getAttribute("id");
			isHidden = Boolean.parseBoolean(tempLauncher.getAttribute("isHidden"));

			warControl.addEnemyLauncher(idLauncher, isHidden);

			NodeList missiles = tempLauncher.getElementsByTagName("missile");

			readMissilesForGivenLauncher(missiles, idLauncher);

		}
	}

	protected void readMissilesForGivenLauncher(NodeList missiles,
			String idLauncher) {
		int launchTime, flyTime, damage;
		String id, destination;
		Element missile;

		for (int i = 0; i < missiles.getLength(); i++) {
			missile = (Element) missiles.item(i);

			id = missile.getAttribute("id");
			destination = missile.getAttribute("destination");

			try {
				launchTime = Integer.parseInt(missile
						.getAttribute("launchTime"));
				flyTime = Integer.parseInt(missile.getAttribute("flyTime"));
				damage = Integer.parseInt(missile.getAttribute("damage"));

			} catch (NumberFormatException e) {
				launchTime = -1;
				flyTime = -1;
				damage = -1;
				System.out.println(e.getStackTrace());
			}

			 createEnemyMissile(id, destination, launchTime, flyTime, damage,
			 idLauncher);
		}
	}

	protected void createEnemyMissile(String missileId, String destination,	int launchTime, int flyTime, int damage, String launcherId) {
		
		final int tempLaunchTime = launchTime, tempDamage = damage;
		final String tempLauncherId = launcherId, tempDestination = destination;

		Thread temp = new Thread() {
			public void run() {
				try {
					sleep(tempLaunchTime * 1000);
					warControl.addEnemyMissile(tempLauncherId, tempDestination, tempDamage);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		temp.start();
	}

	protected void readDefenseDestructors() {
		//System.out.println("\nDefense Destructors:");
		// String type, id;
		NodeList destructors = root.getElementsByTagName("destructor");
		int destructorsSize = destructors.getLength();

		for (int i = 0; i < destructorsSize; i++) {
			Element destructor = (Element) destructors.item(i);

			//System.out.print((i + 1) + ")");
			readDefenseDestructorFromGivenDestructor(destructor);
		}
	}

	protected void readDefenseDestructorFromGivenDestructor(Element destructor) {
		NamedNodeMap attributes = destructor.getAttributes();
		String id = "";
		String type;

		Attr attr = (Attr) attributes.item(0);

		String name = attr.getNodeName();

		if (name.equals("id")) {
			id = attr.getNodeValue();

			warControl.addIronDome(id);
			
			NodeList destructdMissiles = destructor
					.getElementsByTagName("destructdMissile");
			readDefensDestructoreMissiles(destructdMissiles, id);
		} else {
			if (name.equals("type")) {
				type = attr.getNodeValue();
				
				id = warControl.addDefenseLauncherDestractor(type);

				NodeList destructedLanuchers = destructor.getElementsByTagName("destructedLanucher");
				readDefensDestructoreMissiles(destructedLanuchers, id);
			}
		}
	}

	protected void readDefensDestructoreMissiles(NodeList missiles,
			String whoLaunchMeId) {
		int size = missiles.getLength();
		String targetId, destructTimeCheck;
		int destructTime;

		for (int i = 0; i < size; i++) {
			Element missile = (Element) missiles.item(i);
			targetId = missile.getAttribute("id");

			destructTimeCheck = missile.getAttribute("destructAfterLaunch");
			if (destructTimeCheck.equals(""))
				destructTimeCheck = missile.getAttribute("destructTime");

			try {
				destructTime = Integer.parseInt(destructTimeCheck);
			} catch (NumberFormatException e) {
				destructTime = -1;
				System.out.println(e.getStackTrace());
			}

			//System.out.println("\t" + (i + 1) + ") " + targetId + " "+ destructTime);
			createDefenseMissile(whoLaunchMeId, targetId, destructTime);
		}
	}

	protected void createDefenseMissile(String whoLaunchMeId, String targetId, int destructTime) {
		final int tempDestructTime = destructTime;
		final String tempLauncherId = whoLaunchMeId, tempTargetId = targetId;

		Thread temp = new Thread() {
			public void run() {
				try {
					sleep(tempDestructTime * 1000);
					if(tempTargetId.charAt(0) == 'D')
						warControl.interceptGivenMissile(tempLauncherId, tempTargetId);
					else
						warControl.interceptGivenLauncher(tempLauncherId, tempTargetId);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		temp.start();
	}


}
