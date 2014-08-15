package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.enemy.WarEventListener;


//TODO logger & Syso


public class EnemyMissile extends Thread {
	private List<WarEventListener> allListeners;
	
	private String id;
	private String whoLaunchedMeId;
	private String destination;
	private int flyTime;
	private int damage;
	
	public EnemyMissile (String id, String destination, int flyTime, int damage, String whoLaunchedMeId, List<WarEventListener> allListeners){
		allListeners = new LinkedList<WarEventListener>();
		
		this.id = id;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.whoLaunchedMeId = whoLaunchedMeId;
		this.allListeners = allListeners;
	}
	
	public void run() {
		//NEED to fire event that start flying, if success 
		//throw event of succes!!!
		try{
			fireHitEvent();	
			Thread.sleep(flyTime * 1000);
		//Interrupt is thrown when Enemy missile has been hit. 
		}catch(InterruptedException ex){
			//this event was already being thrown by the missile who hit this missile.
		}
		
		
	}
		
	public void fireHitEvent(){
		for (WarEventListener l : allListeners) {
			l.enemyHitDestination(whoLaunchedMeId, id, destination, damage);
		}
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}

	public String getMissileId(){
		return id;
	}

}
