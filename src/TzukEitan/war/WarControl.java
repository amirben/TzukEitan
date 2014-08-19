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
	public void interceptGivenMissile(String ironDomeId, String missileId) {
		warModel.interceptGivenMissile(ironDomeId, missileId);
	}

	@Override
	public void interceptGivenMissile(String missileId) {
		warModel.interceptGivenMissile(missileId);
	}
	
	@Override
	public void interceptGivenLauncher(String launcherId) {
		warModel.interceptGivenLauncher(launcherId);
	}

	@Override
	public void interceptGivenLauncher(String destructorId, String launcherId) {
		warModel.interceptGivenLauncher(destructorId,launcherId);
	}
	
	@Override
	public String[] chooseLauncherToIntercept() {
		String ids[] = warModel.getAllVisibleLaunchersIds();
		return ids;
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
	public String addEnemyLauncher(String launcherId, boolean isHidden) {
		String id = warModel.addEnemyLauncher(launcherId, isHidden);
		return id;
	}
	
	@Override
	public String addEnemyLauncher() {
		String id = warModel.addEnemyLauncher();
		return id;
	}

	@Override
	public String addIronDome() {
		String id = warModel.addIronDome();
		return id;
	}
	
	@Override
	public String addIronDome(String id) {
		String iId = warModel.addIronDome(id);
		return iId;
	}

	@Override
	public String addDefenseLauncherDestractor(String type) {
		String id = warModel.addDefenseLauncherDestractor(type);
		return id;
	}

	@Override
	public String[] getAllWarDestinations() {
		String[] warTargets = warModel.getAllTargetCities();
		return warTargets;
	}

	@Override
	public void warHasBeenFinished() {
		view.showWarHasBeenFinished();
	}

	@Override
	public void warHasBeenStarted() {
		view.showWarHasBeenStarted();
	}

	@Override
	public void noSuchObject(String type) {
		view.showNoSuchObject(type);
	}

	@Override
	public void waitForOrder() {
		view.selectUserChoiseMethod();
	}



	

}
