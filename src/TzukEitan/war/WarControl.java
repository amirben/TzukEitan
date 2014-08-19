package TzukEitan.war;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.listeners.WarEventUIListener;
import TzukEitan.view.ConsoleView;


public class WarControl implements WarEventListener, WarEventUIListener{

	private War warModel;
	private ConsoleView view;
	
	public WarControl(War warModel, ConsoleView view){
		this.warModel = warModel;
		this.view = view;
		
		warModel.registerListenerts(this);
		view.registerListeners(this);
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId) {
		view.showDefenseLaunchMissile(myMunitionsId,missileId,enemyMissileId);
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type,	String missileId, String enemyLauncherId) {
		view.showDefenseLaunchMissile(myMunitionsId, type, missileId, enemyLauncherId);
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId, String destination, int damage) {
		view.showEnemyLaunchMissile(myMunitionsId, missileId, destination, damage);	
	}

	@Override
	public void enemyLauncherIsHidden(String id) {
		view.showLauncherIsHidden(id);
		
	}

	@Override
	public void enemyLauncherIsVisible(String id) {
		view.showLauncherIsVisible(id);
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId) {
		view.showMissInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId) {
		view.showHitInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String missileId, String destination, int damage) {
		view.showEnemyHitDestination(whoLaunchedMeId, missileId, destination, damage);
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,	String type, String missileId, String enemyLauncherId) {
		view.showMissInterceptionLauncher(whoLaunchedMeId,type, enemyLauncherId, missileId);
	}

	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId, String type, String missileId, String enemyLauncherId) {
		view.showHitInterceptionLauncher(whoLaunchedMeId, type, enemyLauncherId, missileId);
	}

	@Override
	public void finishWar() {
		warModel.finishWar();
	}

	@Override
	public void showStatistics() {
		WarStatistics statistics = warModel.getStatistics();
		view.showStatistics(statistics.toArray());	
	}

	@Override
	public String[] chooseMissileToIntercept() {
		String[] ids = warModel.getAllDuringFlyMissilesIds();
		return ids;
	}

	@Override
	public void interceptGivenMissile(String id) {
		warModel.interceptGivenMissile(id);
	}

	@Override
	public String[] chooseLauncherToIntercept() {
		String ids[] = warModel.getAllVisibleLaunchersIds();
		return ids;
	}

	@Override
	public void interceptGivenLauncher(String id) {
		warModel.interceptGivenLauncher(id);
	}

	@Override
	public String[] showAllLaunchers() {
		String ids[] = warModel.getAllLaunchersIds();
		return ids;
	}

	@Override
	public void addEnemyMissile(String launcherId, String destination, int damage) {
		warModel.launchEnemyMissile(launcherId, destination, damage);
	}

	@Override
	public void addEnemyLauncher(String launcherId, boolean isHidden) {
		warModel.addEnemyLauncher(launcherId, isHidden);
	}
	
	@Override
	public void addEnemyLauncher() {
		warModel.addEnemyLauncher();
	}

	@Override
	public void addIronDome() {
		warModel.addIronDome();
	}
	
	@Override
	public void addIronDome(String id) {
		warModel.addIronDome(id);
		
	}

	@Override
	public void addDefenseLauncherDestractor(String type) {
		warModel.addDefenseLauncherDestractor(type);
	}



	

}
