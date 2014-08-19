package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.utils.Utils;
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
	private String launchTime;
	private boolean beenHit = false;
	private static Logger theLogger = Logger.getLogger("myLogger");

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
		launchTime = Utils.getCurrentTime();
		
		try{
			Thread.sleep(flyTime * 1000);
		
		//Interrupt is thrown when Enemy missile has been hit. 
		}catch(InterruptedException ex){
			//this event was already being thrown by the missile who hit this missile.
			theLogger.log(Level.INFO, whoLaunchedMeId +":\t" + destination + "\tlaunch time: " + launchTime +"\tinterception time: " + Utils.getCurrentTime() + "\tintercepted" + "\n");
			beenHit = true;
		}
		
		if (!beenHit)
			fireHitEvent();
	}
		
	public void fireHitEvent(){
		for (WarEventListener l : allListeners) {
			l.enemyHitDestination(whoLaunchedMeId, id, destination, damage);
		}
		statistics.increaseNumOfHitTargetMissiles();
		statistics.increaseTotalDamage(damage);
		theLogger.log(Level.INFO,whoLaunchedMeId +":\t" + destination + "\tlaunch time: " + launchTime +"\tland time: " + Utils.getCurrentTime() + "\tHit - damage: " + damage + "\n");
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}

	public String getMissileId(){
		return id;
	}
	
	public int getDamage(){
		return damage;
	}

}
