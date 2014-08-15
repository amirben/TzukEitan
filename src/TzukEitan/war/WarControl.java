package TzukEitan.war;

import TzukEitan.enemy.WarEventListener;


public class WarControl implements WarEventListener{

	private War warModel;
	private ConsoleView view;
	private WarStatistics statistics;
	
	public WarControl(War warModel, ConsoleView view, WarStatistics statistics){
		this.warModel = warModel;
		this.view = view;
		this.statistics = statistics;
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyLauncherIsHidden(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyLauncherIsVisible(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLunchedMeId,
			String id, String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		// TODO Auto-generated method stub
		
	}



	

}
