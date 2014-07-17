package Enemy;

/** missile for plane, ship and iron dome**/
public class DefenceMissile implements MissileInterface {
	private String id; //My id
	private int destructTime;
	private String idToDestroy;
	
	//TODO Edit cons't
	public DefenceMissile(String id,int destructTime , String idToDestroy){
		this.id = id;
		this.destructTime = destructTime;
		this.idToDestroy = idToDestroy;
	}
	
	public int getDestructTime(){
		return destructTime;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public boolean fire() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasBeenHit() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
