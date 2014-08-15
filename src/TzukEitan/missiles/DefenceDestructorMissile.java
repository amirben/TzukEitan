package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.enemy.WarEventListener;
import TzukEitan.launchers.EnemyLauncher;

/** Missile for plane or ship **/
public class DefenceDestructorMissile extends Thread {
	private List<WarEventListener> allListeners;
	
	private String id;
	private String whoLaunchedMeId;
	private String whoLaunchedMeType;
	private EnemyLauncher LauncherToDestroy;
	private final int LAUNCH_DURATION = 3000;
	
	public DefenceDestructorMissile(String id, EnemyLauncher LauncherToDestroy, String whoLunchedMeId, String whoLaunchedMeType, List<WarEventListener> allListeners ){
		allListeners = new LinkedList<WarEventListener>(); 
		
		this.id = id;
		//this.destructTime = 0;
		this.LauncherToDestroy = LauncherToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.whoLaunchedMeType = whoLaunchedMeType;
		this.allListeners = allListeners;
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
			System.out.println("Thred.sleep in defence destructor missile EROR");
		}
	}

	public void fireHitEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionLauncher(whoLaunchedMeId, whoLaunchedMeType, id, LauncherToDestroy.getLauncherId());
		}
	}
	
	public void fireMissEvent(){
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionLauncher(whoLaunchedMeId, whoLaunchedMeType, id, LauncherToDestroy.getLauncherId());
		}
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
	public String getMissileId(){
		return id;
	}

}
