package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.war.WarStatistics;


//TODO logger & Syso


public class EnemyMissile extends Thread {
	private List<WarEventListener> allListeners;
	
	private String id;
	private String whoLaunchedMeId;
	private String destination;
	private int flyTime;
	private int damage;
	private WarStatistics statistics;
	private boolean beenHit = false;
	
	public EnemyMissile (String id, String destination, int flyTime, int damage, String whoLaunchedMeId, List<WarEventListener> allListeners, WarStatistics statistics){
		allListeners = new LinkedList<WarEventListener>();
		
		this.id = id;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.whoLaunchedMeId = whoLaunchedMeId;
		this.allListeners = allListeners;
		this.statistics = statistics;
	}
	
	public void run() {
		//NEED to fire event that start flying, if success 
		//throw event of succes!!!
		try{
			Thread.sleep(flyTime * 1000);
			//fireHitEvent();
			
		//Interrupt is thrown when Enemy missile has been hit. 
		}catch(InterruptedException ex){
			//this event was already being thrown by the missile who hit this missile.
			beenHit = true;
		}
		if (beenHit)
			fireHitEvent();
		
	}
		
	public void fireHitEvent(){
		for (WarEventListener l : allListeners) {
			l.enemyHitDestination(whoLaunchedMeId, id, destination, damage);
		}
		statistics.increaseNumOfHitTargetMissiles();
		statistics.increaseTotalDamage(damage);
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}

	public String getMissileId(){
		return id;
	}

}
