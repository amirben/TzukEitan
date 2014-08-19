package TzukEitan.launchers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenceDestructorMissile;
import TzukEitan.missiles.DefenceMissile;
import TzukEitan.utils.Utils;
import TzukEitan.utils.WarFormater;
import TzukEitan.war.WarStatistics;


/** plane or ship**/
public class LauncherDestractor extends Thread {
	private List<WarEventListener> allListeners;
	private String type; //plane or ship
	private boolean isRunning = true;
	private EnemyLauncher toDestroy;
	private static int missleIdGen = 100;
	private String id;
	private WarStatistics statistics;
	private static Logger theLogger = Logger.getLogger("warLogger");
	private boolean isBusy = false;

	
	public LauncherDestractor(String type, String id, WarStatistics statistics) throws IOException{
		allListeners = new LinkedList<WarEventListener>();
		
		this.id = id;
		this.type = type;	
		this.statistics = statistics;
		
		addLoggerHandler();
	}
	
	public void run() {
		while (isRunning){
			synchronized (this) {
				try{
					//Wait until user try to destory missile
					wait();
					
					if (toDestroy != null)
						launchMissile();	
				}	
				//Exception is called when launcher has been hit
				catch(InterruptedException ex){
					System.out.println(ex.getStackTrace());
				}
			}
		}
		//TODO add event of finish!
	}

	private void addLoggerHandler() throws IOException {
		FileHandler personHandler;
		personHandler = new FileHandler(type + ":" + id + "Logger.xml", false);
		personHandler.setFilter(new Filter() {
			public boolean isLoggable(LogRecord rec) {
				if (rec.getMessage().contains(id))
					return true;
				return false;
			}
		});
		personHandler.setFormatter(new WarFormater());
		
		theLogger.addHandler(personHandler);
	}
	
	public void setEnemyLauncherToDestroy(EnemyLauncher toDestroy){
		this.toDestroy = toDestroy;
	}

	public void launchMissile() throws InterruptedException {
		String MissileId = idGenerator();
		DefenceDestructorMissile missile = new DefenceDestructorMissile(MissileId, toDestroy, id, type, allListeners, statistics);
		
		fireLaunchMissileEvent(missile.getMissileId());
		
		missile.start();
		missile.join();
	}
	
	public void fireLaunchMissileEvent(String missileId){
		for (WarEventListener l : allListeners) {
			l.defenseLaunchMissile(id, type, missileId, toDestroy.getLauncherId());
		}
	}
	
	public void registerListeners(WarEventListener listener){
		allListeners.add(listener);
	}
	
	public String idGenerator(){
		return "L" + missleIdGen++;
	}
	
	public void stopRunningDestractor(){
		isRunning = false;
	}
	
	public boolean getIsBusy(){
		return isBusy;
	}
}

