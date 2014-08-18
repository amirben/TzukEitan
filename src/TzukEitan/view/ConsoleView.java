package TzukEitan.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import TzukEitan.listeners.WarEventUIListener;

public class ConsoleView {
	private List<WarEventUIListener> allListeners;
	private StringBuilder menu = new StringBuilder(1000);
	private Scanner scanner;

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
				choise = scanner.nextInt();
				flag = true;
			} catch (NumberFormatException e) {
				System.out.println("Worng input, please try again:");
				choise = scanner.nextInt();
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
			fireInterceptMissileLauncher();
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

	private void fireFinishWar() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm:ss");

		System.out.println("==> The War is OVER at: " + ldt.format(dtf));
	}

	private void fireShowStatistics() {
		// TODO Auto-generated method stub

	}

	private void fireInterceptMissile() {
		// TODO Auto-generated method stub

	}

	private void fireInterceptMissileLauncher() {
		// TODO Auto-generated method stub

	}

	private void fireAddEnemyMissile() {
		// TODO Auto-generated method stub

	}

	private void fireAddEnemyLauncher() {
		// TODO Auto-generated method stub

	}

	private void fireAddDefenseIronDome() {
		// TODO Auto-generated method stub

	}

	private void fireAddDefenseLauncherDestractor() {
		// TODO Auto-generated method stub

	}

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
}
