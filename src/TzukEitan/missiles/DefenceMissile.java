package TzukEitan.missiles;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.utils.WarFormater;
import TzukEitan.war.WarStatistics;

/** Missile for iron dome**/
public class DefenceMissile extends Thread {
	private List<WarEventListener> allListeners;
	
	private String id;
	private String whoLunchedMeId;
	private EnemyMissile missileToDestroy;
	private WarStatistics statistics;
	private final int LAUNCH_DURATION = 2000;
	private static Logger theLogger = Logger.getLogger("myLogger");
	
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
			System.out.println(ex.getStackTrace());
		}
	}
	
	private void addLoggerHandler(){
		FileHandler personHandler;
		try {
			personHandler = new FileHandler("Iron dome:" + id + "Logger.xml", false);
			personHandler.setFilter(new Filter() {
				public boolean isLoggable(LogRecord rec) {
					if (rec.getMessage().contains(id))
						return true;
					return false;
				}
			});
			personHandler.setFormatter(new WarFormater());
			
			theLogger.addHandler(personHandler);
			
		} catch (IOException e) {
			e.printStackTrace();
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
		theLogger.log(Level.INFO,whoLunchedMeId +":\t" + missileToDestroy.getMissileId() + "\tSucces");
		
	}
	
	public void fireMissEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionMissile(whoLunchedMeId, id, missileToDestroy.getMissileId());
		}
		theLogger.log(Level.INFO,whoLunchedMeId +":\t" + missileToDestroy.getMissileId() + "\tFail: " + missileToDestroy.getDamage());
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
}
