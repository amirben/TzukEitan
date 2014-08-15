package TzukEitan.war;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.enemy.WarEventListener;

public class ConsoleView {
	private List<WarEventListener> allListeners;
	private StringBuilder menu = new StringBuilder(1000);
	
	public ConsoleView(){
		allListeners = new LinkedList<WarEventListener>();
		createMenu();
	}
	
	public void registerListeners(WarEventListener listener){
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
		System.out.println("Iron dome: " + MunitionsId + " just launched missile: " + "towards missile: " + enemyMissileId);
	}

	public void showDefenseLaunchMissile(String MunitionsId, String type, String missileId, String enemyLauncherId) {
		System.out.println(type + ": " + MunitionsId + " just launched missile: " + "towards launcher: " + enemyLauncherId);
		
	}
}
