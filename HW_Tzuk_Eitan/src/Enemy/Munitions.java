package Enemy;

import java.util.PriorityQueue;

public abstract class Munitions implements Runnable {
	private String id;
	private PriorityQueue<MissileInterface> missileQueue;	
	
	/** Occur when launcher want to fire missile **/
	public abstract boolean launchMissile();
	
	
}
