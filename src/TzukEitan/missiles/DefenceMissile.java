package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.war.WarStatistics;

/** Missile for iron dome**/
public class DefenceMissile extends Thread {
	private List<WarEventListener> allListeners;
	
	private String id;
	private String whoLunchedMeId;
	private EnemyMissile missileToDestroy;
	private WarStatistics statistics;
	private final int LAUNCH_DURATION = 2000;
	
	public DefenceMissile(String id, EnemyMissile missileToDestroy, String whoLunchedMeId, List<WarEventListener> allListeners, WarStatistics statistics){
		allListeners = new LinkedList<WarEventListener>();
		this.id = id;
		this.missileToDestroy = missileToDestroy;
		this.whoLunchedMeId = whoLunchedMeId;
		this.allListeners = allListeners;
		this.statistics = statistics;
	}

	public void run() {
		try{
			//Launch duration
			Thread.sleep(LAUNCH_DURATION);
			
			//Check if the missile is still in the air before trying to destroy
			synchronized (this) {
				if (missileToDestroy.isAlive()){
					missileToDestroy.interrupt();
					fireHitEvent();
				}
				else{
					fireMissEvent();
				}
			}
			
		}catch(InterruptedException ex){
			//TODO add logger
			System.out.println("Thred.sleep in EnemyMissile EROR");
		}
	}
	
	public String getMissileId(){
		return id;
	}

	public void fireHitEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionMissile(whoLunchedMeId, id, missileToDestroy.getMissileId());
		}
		statistics.increaseNumOfInterceptMissiles();
		
	}
	
	public void fireMissEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionMissile(whoLunchedMeId, id, missileToDestroy.getMissileId());
		}
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}


	
}
