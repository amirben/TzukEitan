package Enemy;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class Munitions implements Runnable {
	private String id;
	private PriorityBlockingQueue<MissileInterface> missileQueue;	
	
	public Munitions(String id,MissileComparator missileComp){
		this.id = id;
		missileQueue = new PriorityBlockingQueue(0, missileComp);
	}
	
	public void addMissile(MissileInterface missile){
		try{
			missileQueue.add(missile);
		}catch(NullPointerException ex){
			System.out.println("Missile queue is not exist");
		};
	}
	
	/** Occur when launcher want to fire missile **/
	public abstract boolean launchMissile();
	
	
}
