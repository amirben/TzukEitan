package TzukEitan.launchers;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.enemy.WarEventListener;
import TzukEitan.missiles.DefenceDestructorMissile;
import TzukEitan.missiles.DefenceMissile;
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
	
	public LauncherDestractor(String type, String id, WarStatistics statistics){
		allListeners = new LinkedList<WarEventListener>();
		
		this.id = id;
		this.type = type;	
		this.statistics = statistics;
	}
	
	public void run() {
		while (!isRunning){
			synchronized (this) {
				try{
					//Wait until user try to destory missile
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
	}

	public void setEnemyLauncherToDestroy(EnemyLauncher toDestroy){
		this.toDestroy = toDestroy;
	}

	public boolean launchMissile() {
		String MissileId = idGenerator();
		DefenceDestructorMissile missile = new DefenceDestructorMissile(MissileId, toDestroy, id, type, allListeners, statistics);
		
		fireLaunchMissileEvent(missile.getMissileId());
		missile.start();
		
		return true;
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
}

//class MissileComparator implements Comparator<MissileInterface>{
//	@Override
//	public int compare(MissileInterface o1, MissileInterface o2) {
//		if (o1 instanceof EnemyMissile)
//			return ((EnemyMissile)o2).getLaunchTime() - ((EnemyMissile)o1).getLaunchTime();
//		
//		else
//			if (o1 instanceof DefenceMissile)
//				return ((DefenceMissile)o2).getDestructTime() - ((DefenceMissile)o1).getDestructTime();
//		
//		return 0;
//	}
//	
//}
