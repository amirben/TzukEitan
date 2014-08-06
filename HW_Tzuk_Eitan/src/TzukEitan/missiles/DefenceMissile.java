package TzukEitan.missiles;

import Enemy.MissileEvent;

/** missile for plane, ship and iron dome**/
public class DefenceMissile extends Thread implements MissileEvent {
	private String id; //My id
	private int destructTime;
	private EnemyMissile missileToDestroy;
	private final int LAUNCH_DURATION = 2000;
	
	//TODO Edit cons't
	public DefenceMissile(String id, int destructTime, EnemyMissile missileToDestroy){
		this.id = id;
		this.destructTime = destructTime;
		this.missileToDestroy = missileToDestroy;
	}
	
	public DefenceMissile(String id, EnemyMissile missileToDestroy){
		this.id = id;
		this.destructTime = 0;
		this.missileToDestroy = missileToDestroy;
	}
	
	public int getDestructTime(){
		return destructTime;
	}

	public void run() {
		//TODO change this syso to event
		System.out.println("Defence missile "+ id +" is being launch to destroy missile "+missileToDestroy.getMissileId());
		try{
			//Use for the XML version
			Thread.sleep(1000 * destructTime);
			//Launch duration
			Thread.sleep(LAUNCH_DURATION);
			fire();
			
			if (missileToDestroy.isAlive()){
				//TODO logger
				//TODO throw event hit
				missileToDestroy.interrupt();
				hasBeenHit();
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