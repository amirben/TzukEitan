package Enemy;


public class EnemyMissile implements MissileInterface{
	
	//Need to add attributes
	private String id;
	private String destination;
	private int launchTime;
	private int flyTime;
	private int damage;
	// TODO
	// Add region to missile
	
	
	//TODO Edit Cons't
	public EnemyMissile(){
		
	}
	
	public void run() {
		
		
	}

	public boolean fire() {
		return false;
	}
	
	public boolean hasBeenHit(){
		return false;
	}

}
