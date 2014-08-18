package TzukEitan.launchers;


import java.util.LinkedList;
import java.util.List;

import TzukEitan.enemy.WarEventListener;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.war.WarControl;
import TzukEitan.war.WarStatistics;

//TODO logger & Syso


public class EnemyLauncher extends Thread {
	private List<WarEventListener> allListeners;
	
	private boolean beenHit = false;
	private boolean isHidden;
	private boolean firstHiddenState;
	private String id;
	private String destination;
	private int damage;
	private EnemyMissile currentMissile;
	private WarStatistics statistics;
	private static int missleIdGen;
	private final int LAUNCH_DURATION = 2000;
	private WarControl control;
    	
	public EnemyLauncher(String id, boolean isHidden, WarControl control, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>(); 
		this.id = id;
		this.isHidden = isHidden;
		firstHiddenState = isHidden;
		missleIdGen = 100;
		this.control = control;
		this.statistics = statistics;
	}

	public void run() {
		
		while(!beenHit){
			synchronized (this) {
				try{
					//Wait until user want to fire a missile
					wait();
					launchMissile();	
				}	
				//Exception is called when launcher has been hit
				catch(InterruptedException ex){
					beenHit = true;
					//firehasBeenHitEvent();
					//not needed because the DefenseDestructorMissile call this event
				}
			}	
		}
	}

	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public void setDestination(String dest){
		this.destination = dest;
	}
	
	public boolean launchMissile() throws InterruptedException{
		//TODO logger
		
		currentMissile = createMissile();
		
		fireLaunchMissileEvent(currentMissile.getMissileId());
		
		//Missile isn't hiding when launching a missile
		isHidden = false;
		
		//It's take time to launch missile
		sleep(LAUNCH_DURATION);
		currentMissile.start();
		
		//X time that the launcher is not hidden:
		int x = (int) Math.random() * 5000;
		sleep(x);
		isHidden = firstHiddenState;
		
		currentMissile.join();	

		return true;
	}
	
	public void fireLaunchMissileEvent(String missileId){
		for (WarEventListener l : allListeners) {
			l.enemyLaunchMissile(id, missileId, destination, damage);
		}
		statistics.increaseNumOfLaunchMissiles();
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
	//Create new missile
	public EnemyMissile createMissile(){
		String missileId = idGenerator();
		int flyTime = (int) Math.random() * 3000;
		EnemyMissile missile = new EnemyMissile(missileId, destination, flyTime , damage, id, allListeners, statistics);
		
		return missile;
	}
	
	public String idGenerator(){
		return "M" + missleIdGen++;
	}
	
	public String getLauncherId(){
		return id;
	}
	
	public boolean getIsHidden(){
		return isHidden;
	}

}
