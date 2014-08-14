package TzukEitan.launchers;


import TzukEitan.missiles.EnemyMissile;
import TzukEitan.war.WarControl;

//TODO logger & Syso


public class EnemyLauncher extends Thread {
	
	private boolean beenHit = false;
	private boolean isHidden;
	private boolean firstHiddenState;
	private String id;
	private String destination;
	private int damage;
	private EnemyMissile currentMissile;
	private static int missleIdGen;
	private final int LAUNCH_DURATION = 2000;
	private WarControl control;
    	
	public EnemyLauncher(String id, boolean isHidden, WarControl control) {
		this.id = id;
		this.isHidden = isHidden;
		firstHiddenState = isHidden;
		missleIdGen = 100;
		this.control = control;
	}

	public void run() {
		
		while(!beenHit){
			synchronized (this) {
				try{
					//Wait until user want to fire a missile
					wait();
					launchMissile();	
				}	
				//Exception is called when launcher has been hit
				catch(InterruptedException ex){
					hasBeenHit();
				}
			}	
		}
	}

	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public void setDestination(String dest){
		this.destination = dest;
	}
	
	public void hasBeenHit(){
		System.out.println("Launcher " + getLauncherId() + "has been hit");
		beenHit = true;
		//TODO logger
	}
	
	public boolean launchMissile(){
		//TODO logger
		control.
		currentMissile = createMissile();
		try {
			//Missle isn't hide when launch missile
			isHidden = false;
			
			//It's take time to launch missile
			sleep(LAUNCH_DURATION);
			currentMissile.start();
			
			//X time that the launcher is not hidden:
			int x = (int) Math.random() * 5000;
			sleep(x);
			isHidden = firstHiddenState;
			
			currentMissile.join();
			
		} catch (InterruptedException e) {
			//if we are here then while the missile is in the air we have been hit
			hasBeenHit();
			return false;
		}
		return true;
	}
	
	//Create new missile
	public EnemyMissile createMissile(){
		String missileId = idGenerator();
		int flyTime = (int) Math.random() * 3000;
		EnemyMissile missile = new EnemyMissile(missileId, destination, flyTime , damage);
		
		return missile;
	}
	
	public String idGenerator(){
		return "M" + missleIdGen++;
	}
	
	public String getLauncherId(){
		return id;
	}
	
	public boolean getIsHidden(){
		return isHidden;
	}

}
