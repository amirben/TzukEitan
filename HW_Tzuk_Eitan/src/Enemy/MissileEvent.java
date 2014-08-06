package Enemy;

public interface MissileEvent{
		
	
	/** Need to implement - return boolean if success **/
	public boolean fire();
	
	/** This methods called when the missile has been hit and destroyed, and want to end Thread **/
	public boolean hasBeenHit();
		
	
}
