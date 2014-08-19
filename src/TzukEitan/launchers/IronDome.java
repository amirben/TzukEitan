package TzukEitan.launchers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenceMissile;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.utils.IdGenerator;
import TzukEitan.utils.WarFormater;
import TzukEitan.war.WarStatistics;

public class IronDome extends Thread {
	private List<WarEventListener> allListeners;
	private EnemyMissile toDestroy;
	private String id;
	private WarStatistics statistics;
	private boolean isRunning = true;
	private boolean isBusy = false;
	private static Logger theLogger = Logger.getLogger("warLogger");
	
	public IronDome(String id, WarStatistics statistics){
		allListeners = new LinkedList<WarEventListener>(); 
		this.statistics = statistics;
		this.id = id;
		
		addLoggerHandler();
	}

	public void run() {
		while(isRunning){
			synchronized (this) {
				try{
					//Wait until user will try to destory launcher
					wait();
					isBusy = true;
					if (toDestroy != null)
						launchMissile();	
				}	
				//Exception is called when launcher has been hit
				catch(InterruptedException ex){
					//TODO something
				}
			}
			isBusy = false;
		}
		//TODO close thread correctly maybe throw exception
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

	public void setMissileToDestroy(EnemyMissile toDestroy){
		this.toDestroy = toDestroy;
	}
	
	public void launchMissile() throws InterruptedException {
		String missieId = IdGenerator.defensMissileIdGenerator();
		DefenceMissile missile = new DefenceMissile(missieId , toDestroy, id, allListeners, statistics);
		
		fireLaunchMissileEvent(missile.getMissileId());
		missile.start();
		missile.join();
	}
	
	public void fireLaunchMissileEvent(String missileId){
		for (WarEventListener l : allListeners) {
			l.defenseLaunchMissile(id, missileId, toDestroy.getMissileId());
		}
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
	public void stopRunningIronDome(){
		isRunning = false; 
	}
	
	public boolean getIsBusy(){
		return isBusy;
	}

	public String getIronDomeId() {
		return id;
	}
}
