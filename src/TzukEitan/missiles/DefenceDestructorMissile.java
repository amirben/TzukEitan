package TzukEitan.missiles;

import TzukEitan.Enemy.MissileEvent;
import TzukEitan.launchers.EnemyLauncher;

/** Missile for plane or ship **/
public class DefenceDestructorMissile extends Thread implements MissileEvent {
	private String id;
	//private int destructTime;
	private EnemyLauncher LauncherToDestroy;
	private final int LAUNCH_DURATION = 3000;
	
//	//TODO Edit cons't
//	public DefenceDestructorMissile(String id, int destructTime, EnemyLauncher LauncherToDestroy){
//		this.id = id;
//		this.destructTime = destructTime;
//		this.LauncherToDestroy = LauncherToDestroy;
//	}
	
	public DefenceDestructorMissile(String id, EnemyLauncher LauncherToDestroy){
		this.id = id;
		//this.destructTime = 0;
		this.LauncherToDestroy = LauncherToDestroy;
	}

	public void run() {
		//TODO change this syso to event
		//System.out.println("Defence Destructor missile "+ id +" is being launch to destroy EnemyLauncher "+LauncherToDestroy.getLauncherId());
		
		try{
//			//Use for the XML version
//			Thread.sleep(1000 * destructTime);
			
			//Launch duration
			Thread.sleep(LAUNCH_DURATION);
			fire();
			
			if (LauncherToDestroy.isAlive()){
				//TODO logger
				//TODO throw event hit
				
				//Check if the launcher is hidden or not
				if (!LauncherToDestroy.getIsHidden()){
					LauncherToDestroy.interrupt();
					hasBeenHit();
				}
			}
			else{
				//TODO throw event not hit
			}
			
		//Interrupt is thrown when Enemy missile has been hit. 
		}catch(InterruptedException ex){
			hasBeenHit();
			//TODO add logger
			
		}catch(Exception ex){
			System.out.println("Thred.sleep in EnemyMissile EROR");
			//TODO add logger
		}
	}

	public boolean fire() {
		// TODO Auto-generated method stub
		//Create event fire
		return false;
	}

	public boolean hasBeenHit() {
		// TODO Auto-generated method stub
		//event
		return false;
	}
	
}
