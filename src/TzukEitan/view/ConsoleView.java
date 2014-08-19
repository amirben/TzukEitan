package TzukEitan.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.utils.Utils;

public class ConsoleView {
	private List<WarEventUIListener> allListeners;
	private Scanner input = new Scanner(System.in);
	private StringBuilder menu = new StringBuilder(1000);
	
	public ConsoleView() {
		allListeners = new LinkedList<WarEventUIListener>();
		createMenu();
	}

	public void registerListeners(WarEventUIListener listener) {
		allListeners.add(listener);
	}

	private void createMenu() {
		menu.append("choose one option: \n\n");
		menu.append("1. Add Munition to Intercept launchers.\n");
		menu.append("2. Add Munition to Intercept missile.\n");
		menu.append("3. Add launcher.\n");
		menu.append("4. Launch a missile.\n");
		menu.append("5. Intercept a launcher.\n");
		menu.append("6. Intercept a missile.\n");
		menu.append("7. Show statistics.\n");
		menu.append("8. End the war and show statistics.\n");
	}

	private int readUserChoise() {
		boolean flag = false;
		int choise = -1;
		System.out.println(menu);

		while (!flag) {
			try {
				choise = input.nextInt();
				flag = true;
			} catch (NumberFormatException e) {
				System.out.println("Worng input, please try again:");
				choise = input.nextInt();
			}
		}

		return choise;
	}

	public void selectUserChoiseMethod() {
		int choise = readUserChoise();

		switch (choise) {
		case 1:
			fireAddDefenseLauncherDestractor();
			break;

		case 2:
			fireAddDefenseIronDome();
			break;

		case 3:
			fireAddEnemyLauncher();
			break;

		case 4:
			fireAddEnemyMissile();
			break;

		case 5:
			fireInterceptEnemyLauncher();
			break;

		case 6:
			fireInterceptMissile();
			break;

		case 7:
			fireShowStatistics();
			break;

		case 8:
			fireFinishWar();
			break;
		}
	}

	/* User commands */
	private void fireAddDefenseLauncherDestractor() {
		System.out.println("Please choose between Plane or Ship, for exit press enter");
		String type = input.nextLine();
		type = type.toLowerCase();
		
		if (type.equals("plane") || type.equals("ship"))
			for (WarEventUIListener l : allListeners)
				l.addDefenseLauncherDestractor(type);
	}
	
	private void fireAddDefenseIronDome() {
		for (WarEventUIListener l : allListeners)
			l.addIronDome();
	}

	private void fireAddEnemyLauncher() {
		for (WarEventUIListener l : allListeners)
			l.addEnemyLauncher();
	}
	
	private void fireAddEnemyMissile() {
		String[] launcersIds;
		String launcher;
		String[] destinations;
		
		for (WarEventUIListener l : allListeners){
			launcersIds = l.showAllLaunchers();
			
			if (launcersIds != null){
				System.out.println("Launchers to launch with:");
			
				for (int i = 0 ; i < launcersIds.length ; i++)
					System.out.println("\t" + (i+1) + ")" + launcersIds[i]);
					
				System.out.println("Choose launcher id to equip, else press enter to continue");
				launcher = input.nextLine();
				
				if (!launcher.equals("")){
					System.out.println("Destination cities to destory:");
					destinations = l.getAllWarDestinations();
					
					for(int j=0 ; j<destinations.length ; j++)
						System.out.println((j+1) + ") " + destinations[j]);
					
					System.out.println("Enter your choise:");
					String destination = input.nextLine();
					
					int damage = (int)((Math.random()*1000) + 2000);
					l.addEnemyMissile(launcher, destination, damage);
				}
			}
			else
				System.out.println("There is no launcher yet, please add launcher first");
			
		}
	}

	private void fireInterceptEnemyLauncher() {
		String launcersId[];
		String launcher;
		
		for (WarEventUIListener l : allListeners){
			launcersId = l.chooseLauncherToIntercept();
			
			if (launcersId !=null){
				System.out.println("Launcher to intercept:");
				
				for (int i = 0 ; i < launcersId.length; i++)
					System.out.println("\t" + (i+1) + ")" + launcersId[i]);
					
				System.out.println("Choose launcher id to intercept, else press enter to continue");
				launcher = input.nextLine();
				
				if (!launcher.equals(""))
					l.interceptGivenLauncher(launcher);
			}
			else 
				System.out.println("There is no missiles to intercept!");
		}	
	}

	private void fireInterceptMissile() {
		String[] missilesId;
		String missile;

		for (WarEventUIListener l : allListeners) {
			missilesId = l.chooseMissileToIntercept();

			if (missilesId != null) {
				System.out.println("Missiles to intercept:");
				for (int i = 0; i < missilesId.length; i++)
					System.out.println("\t" + (i + 1) + ")" + missilesId[i]);

				System.out.println("Choose missile id to intercept, else press enter to continue");
				missile = input.nextLine();
				
				if (!missile.equals(""))
					l.interceptGivenMissile(missile);
			}
			else 
				System.out.println("There is no missiles to intercept!");
		}
	}
	

	private void fireShowStatistics() {
		for (WarEventUIListener l : allListeners)
			l.showStatistics();
	}

	private void fireFinishWar() {
		for (WarEventUIListener l : allListeners)
			l.finishWar();
		
		System.out.println("==> The War is OVER at: " + Utils.getCurrentTime());
	}

	/* Prints to screen event from controller */
	public void showDefenseLaunchMissile(String MunitionsId, String missileId,
			String enemyMissileId) {
		System.out.println("Iron dome: " + MunitionsId
				+ " just launched missile: " + " towards missile: "
				+ enemyMissileId);
	}

	public void showDefenseLaunchMissile(String MunitionsId, String type,
			String missileId, String enemyLauncherId) {
		System.out.println(type + ": " + MunitionsId
				+ " just launched missile: " + " towards launcher: "
				+ enemyLauncherId);
	}

	public void showEnemyLaunchMissile(String MunitionsId, String missileId,
			String destination, int damage) {
		System.out.println("Launcher: " + MunitionsId
				+ " just launched missile: " + missileId + " towards: "
				+ destination + " its about to cause damade of: " + damage);
	}

	public void showLauncherIsHidden(String id) {
		System.out.println("Launcher: " + id + " just turned Hidden again");
	}

	public void showLauncherIsVisible(String id) {
		System.out.println("Launcher: " + id + " just turned visible");
	}

	public void showMissInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		System.out.println("Iron Dome: " + whoLaunchedMeId + " fired missile: "
				+ id + " but missed the missile: " + enemyMissileId);
	}

	public void showHitInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		System.out.println("Iron Dome: " + whoLaunchedMeId + " fired missile: "
				+ id + " and intercept succesfully the missile: "
				+ enemyMissileId);
	}

	public void showEnemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage) {
		System.out.println("Enemy Launcher: " + whoLaunchedMeId + " caused "
				+ damage + " damage to: " + destination + ", with missile: "
				+ id);
	}

	public void showMissInterceptionLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId, String missileId) {
		System.out.println(type + whoLaunchedMeId + " fired missile: "
				+ missileId + " but missed the Launcher: " + enemyLauncherId);
	}

	public void showHitInterceptionLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId, String missileId) {
		System.out.println(type + whoLaunchedMeId + " fired missile: "
				+ missileId + " and intercept succesfully the Launcher: "
				+ enemyLauncherId);
	}

	public void showStatistics(long[] array) {
		StringBuilder msg = new StringBuilder();
		msg.append("[" + Utils.getCurrentTime() + "]" + "==> War Statistics");
		msg.append("Num of launch missiles: " + array[0]+"\n");
		msg.append("Num of intercept missiles: " + array[1]+"\n");
		msg.append("Num of hit target missiles: " + array[2]+"\n");
		msg.append("Num of launchers destroyed: " + array[3]+"\n");
		msg.append("total damage: " + array[0]+"\n");

		System.out.println(msg.toString());	
	}
	
	public void showWarHasBeenFinished(){
		System.out.println("=========>> Finally THIS WAR IS OVER!!! <<=========");
		System.out.println("[" + Utils.getCurrentTime() + "]");
	}
	
	public void showWarHasBeenStarted(){
		System.out.println("=========>> War has been strated!!! <<=========");
		System.out.println("[" + Utils.getCurrentTime() + "]");
	}

	public void showNoSuchObject(String type) {
		System.out.println("[" + Utils.getCurrentTime() + "] Cannot find " + type + " you selected in war");
	}
}
