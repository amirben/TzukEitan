package TzukEitan.missiles;

import Enemy.MissileEvent;

//TODO logger & Syso


public class EnemyMissile extends Thread implements MissileEvent {
	
	//Need to add attributes
	private String id;
	private String destination;
	private int launchTime;
	private int flyTime;
	private int damage;
	// TODO Add region to missile
	
	
	//TODO Edit Cons't
//	public EnemyMissile(String id, String destination,int launchTime, int flyTime,int damage){
//		this.id = id;
//		this.destination = destination;
//		this.launchTime = launchTime;
//		this.flyTime = flyTime;
//		this.damage = damage;
//	}
	
	public EnemyMissile(String id, String destination, int flyTime,int damage){
		this.id = id;
		this.destination = destination;
		this.launchTime = 0;
		this.flyTime = flyTime;
		this.damage = damage;
	}
	
	public void run() {
		System.out.println("Enemy missile "+ id +" is being launch to "+destination);
		try{
			//TODO logger
			Thread.sleep(flyTime * 1000);
			damageDestination();
			
		//Interrupt is thrown when Enemy missile has been hit. 
		}catch(InterruptedException ex){
			hasBeenHit();
			//TODO add logger
			
		}catch(Exception ex){
			System.out.println("Thred.sleep in EnemyMissile EROR");
			//TODO add logger
		}
	}
	
	public void damageDestination( ){
		System.out.println("Enemy missile: " + id + " hit the destination: " + destination);
		//TODO logger
	}
	public int getLaunchTime(){
		return launchTime;
	}
	

	public boolean fire() {
		return false;
	}
	
	public boolean hasBeenHit(){
		//TODO add time
		System.out.println("Enemy missile "+ id +" has been destroyed by Iron Dome in time: ");
		//TODO logger
		return true;
	}
	
	public String getMissileId(){
		return id;
	}

}
