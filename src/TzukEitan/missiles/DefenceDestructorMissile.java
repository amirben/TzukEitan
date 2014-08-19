package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.launchers.EnemyLauncher;
import TzukEitan.utils.Utils;
import TzukEitan.war.WarStatistics;

/** Missile for plane or ship **/
public class DefenceDestructorMissile extends Thread {
	private List<WarEventListener> allListeners;
	
	private String id;
	private String whoLaunchedMeId;
	private String whoLaunchedMeType;
	private EnemyLauncher LauncherToDestroy;
	private WarStatistics statistics;
	private final int LAUNCH_DURATION = 3000;
	private static Logger theLogger = Logger.getLogger("myLogger");
	
	static {
		theLogger.setUseParentHandlers(false);
	}
	
	public DefenceDestructorMissile(String id, EnemyLauncher LauncherToDestroy, String whoLunchedMeId, String whoLaunchedMeType, List<WarEventListener> allListeners, WarStatistics statistics ){
		allListeners = new LinkedList<WarEventListener>(); 
		
		this.id = id;
		//this.destructTime = 0;
		this.LauncherToDestroy = LauncherToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.whoLaunchedMeType = whoLaunchedMeType;
		this.allListeners = allListeners;
		this.statistics = statistics;
	}

	public void run() {
		try{			
			//Launch duration
			Thread.sleep(LAUNCH_DURATION);
			
			synchronized (this) {
				if (LauncherToDestroy.isAlive()){
					//Check if the launcher is hidden or not
					if (!LauncherToDestroy.getIsHidden()){
						LauncherToDestroy.interrupt();
						fireHitEvent();
					}
				}
				else{
					fireMissEvent();
				}
			}
			
		}catch(InterruptedException ex){
			System.out.println(ex.getStackTrace());
		}
	}

	public void fireHitEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionLauncher(whoLaunchedMeId, whoLaunchedMeType, id, LauncherToDestroy.getLauncherId());
		}
		statistics.increaseNumOfLauncherDestroyed();
		theLogger.log(Level.INFO,whoLaunchedMeId + ":\t"+ LauncherToDestroy.getLauncherId() + "\tSucces"  + "\n");	
	}
	
	public void fireMissEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionLauncher(whoLaunchedMeId, whoLaunchedMeType, id, LauncherToDestroy.getLauncherId());
		}
		theLogger.log(Level.INFO,whoLaunchedMeId + ":\t"+ LauncherToDestroy.getLauncherId() + "\tFail"  + "\n");
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
	public String getMissileId(){
		return id;
	}

}
