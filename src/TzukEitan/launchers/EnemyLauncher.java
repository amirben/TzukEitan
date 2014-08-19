package TzukEitan.launchers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.utils.IdGenerator;
import TzukEitan.utils.WarFormater;
import TzukEitan.war.WarControl;
import TzukEitan.war.WarStatistics;


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
	private final int LAUNCH_DURATION = 2000;
	private static Logger theLogger = Logger.getLogger("warLogger");
    	
	public EnemyLauncher(String id, boolean isHidden, WarStatistics statistics){
		this.id = id;
		this.isHidden = isHidden;
		this.statistics = statistics;

		allListeners = new LinkedList<WarEventListener>(); 
		firstHiddenState = isHidden;
		
		addLoggerHandler();
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
					stopEnemyLaucher();
					//firehasBeenHitEvent() ==> not needed because
					//the DefenseDestructorMissile call this event
				}
			}	
			currentMissile = null;
		}
	}

	private void addLoggerHandler(){
		FileHandler personHandler;
		try {
			personHandler = new FileHandler("Launcher" + id + "Logger.xml", false);
			personHandler.setFilter(new Filter() {
				public boolean isLoggable(LogRecord rec) {
					if (rec.getMessage().contains(id))
						return true;
					return false;
					//TODO use getParameters();
				}
			});
			personHandler.setFormatter(new WarFormater());
			
			theLogger.addHandler(personHandler);
			
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setMissileInfo(String destination, int damage){
		this.destination = destination;
		this.damage = damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public void setDestination(String dest){
		this.destination = dest;
	}
	
	//may need synchronized
	public void launchMissile() throws InterruptedException{
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
		String missileId = IdGenerator.enemyMissileIdGenerator();
		int flyTime = (int) Math.random() * 3000;
		EnemyMissile missile = new EnemyMissile(missileId, destination, flyTime , damage, id, allListeners, statistics);
		
		return missile;
	}
	
	public String getLauncherId(){
		return id;
	}
	
	public boolean getIsHidden(){
		return isHidden;
	}
	
	public EnemyMissile getCurrentMissile(){
		if(currentMissile != null && currentMissile.isAlive())
			return currentMissile;
		
		return null;
	}
	
	public void stopEnemyLaucher(){
		beenHit = true;
	}
}
