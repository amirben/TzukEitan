package TzukEitan.war;

import TzukEitan.enemy.MissileEventListener;
import TzukEitan.enemy.MunitionsEventListener;

public class WarControl implements MissileEventListener,MunitionsEventListener{

	private War warModel;
	private ConsoleView view;
	
	public warControl(War warModel,)
	@Override
	public void defenceLaunchMissile(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyLauncherFireMissile(String id) {
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
	public void defenceLaunchMissile(String id, String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defenceMissInterception(String id, String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defenceHitInterception(String id, String enemyMissileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyLaunchMissile(String id, String destination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyHitDestination(String id, String destination, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyInterception(String id, String destination) {
		// TODO Auto-generated method stub
		
	}

}
