package TzukEitan.launchers;

import Enemy.MunitionsEvent;


/** plane or ship**/
public class LauncherDestractor extends Thread implements MunitionsEvent {
	private String type; //plane or ship
	private EnemyLauncher toDestroy;
	private String id;
	
	public LauncherDestractor(String type, String id){
		this.id = id;
		this.type = type;	
	}
	
	public void run() {
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

	public void setEnemyLauncherToDestroy(EnemyLauncher toDestroy){
		this.toDestroy = toDestroy;
	}

	public boolean launchMissile() {
		// TODO Auto-generated method stub
		/** ADD EVENT **/
		return false;
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
