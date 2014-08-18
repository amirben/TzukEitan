package TzukEitan.launchers;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenceMissile;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.war.WarStatistics;

public class IronDome extends Thread {
	private List<WarEventListener> allListeners;
	
	private EnemyMissile toDestroy;
	private static int missleIdGen;
	private String id;
	private WarStatistics statistics;
	private boolean isRunning = true;
	
	public IronDome(String id, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>(); 
		this.id = id;
		missleIdGen = 100;
		this.statistics = statistics;
	}

	public void run() {
		while(isRunning){
			synchronized (this) {
				try{
					//Wait until user will try to destory launcher
					wait();
					
					if (toDestroy != null)
						launchMissile();	
				}	
				//Exception is called when launcher has been hit
				catch(InterruptedException ex){
					//TODO something
				}
			}
		}
		//TODO close thread correctly
	}

	public void setMissileToDestroy(EnemyMissile toDestroy){
		this.toDestroy = toDestroy;
	}
	
	public boolean launchMissile() {
		String missieId = idGenerator();
		DefenceMissile missile = new DefenceMissile(missieId , toDestroy, id, allListeners, statistics);
		
		fireLaunchMissileEvent(missile.getMissileId());
		missile.start();
		
		return true;
	}
	
	public void fireLaunchMissileEvent(String missileId){
		for (WarEventListener l : allListeners) {
			l.defenseLaunchMissile(id, missileId, toDestroy.getMissileId());
		}
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
	//Generate new ID
	public String idGenerator(){
		return "M" + missleIdGen++;
	}
	
	public void stopRunningIronDome(){
		isRunning = false; 
	}
}
