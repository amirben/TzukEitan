package TzukEitan.war;

import java.util.LinkedList;
import java.util.List;
import TzukEitan.listeners.WarEventUIListener;

public class ConsoleView {
	private List<WarEventUIListener> allListeners;
	private StringBuilder menu = new StringBuilder(1000);
	
	public ConsoleView(){
		allListeners = new LinkedList<WarEventUIListener>();
		createMenu();
	}
	
	public void registerListeners(WarEventUIListener listener){
		allListeners.add(listener);
	}
	
	private void createMenu(){
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

	public void showDefenseLaunchMissile(String MunitionsId, String missileId, String enemyMissileId) {
		System.out.println("Iron dome: " + MunitionsId + " just launched missile: " + " towards missile: " + enemyMissileId);
	}

	public void showDefenseLaunchMissile(String MunitionsId, String type, String missileId, String enemyLauncherId) {
		System.out.println(type + ": " + MunitionsId + " just launched missile: " + " towards launcher: " + enemyLauncherId);	
	}

	public void showEnemyLaunchMissie(String MunitionsId, String missileId, String destination, int damage) {
		System.out.println("Launcher: " + MunitionsId + " just launched missile: " + missileId + " towards: " + destination + " its about to cause damade of: " + damage);
	}

	public void showLauncherIsHidden(String id) {
		System.out.println("Launcher: " + id + " just turned Hidden again");
	}
	
	public void showLauncherIsVisible(String id) {
		System.out.println("Launcher: " + id + " just turned visible");
	}

	public void showMissInterceptionMissile(String whoLaunchedMeId, String id, String enemyMissileId) {
		System.out.println("Iron Dome: " + whoLaunchedMeId + " fired missile: " + id + " but missed the missile: " + enemyMissileId);
	}

	public void showHitInterceptionMissile(String whoLaunchedMeId, String id, String enemyMissileId) {
		System.out.println("Iron Dome: " + whoLaunchedMeId + " fired missile: " + id + " and intercept succesfully the missile: " + enemyMissileId);
	}

	public void showEnemyHitDestination(String whoLaunchedMeId, String id, String destination, int damage) {
		System.out.println("Enemy Launcher: " + whoLaunchedMeId + " caused " + damage + " damage to: " + destination + ", with missile: " + id);
	}

	public void showMissInterceptionLauncher(String whoLaunchedMeId, String type, String enemyLauncherId, String missileId) {
		System.out.println(type + whoLaunchedMeId + " fired missile: " + missileId + " but missed the Launcher: " + enemyLauncherId);
	}

	public void showHitInterceptionLauncher(String whoLaunchedMeId, String type, String enemyLauncherId, String missileId) {
		System.out.println(type + whoLaunchedMeId + " fired missile: " + missileId + " and intercept succesfully the Launcher: " + enemyLauncherId);
	}
}
