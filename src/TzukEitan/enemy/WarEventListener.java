package TzukEitan.enemy;

public interface WarEventListener {

	/** Defense Iron Dome launch interception missile **/
	public void defenseLaunchMissile (String myMunitionsId,String missileId,String enemyMissileId);
	
	/** Defense Airplane or ship launch interception launcher **/
	public void defenseLaunchMissile (String myMunitionsId, String type, String missileId,String enemyLauncherId);
	
	/** Enemy Launch missile **/
	public void enemyLaunchMissile (String myMunitionsId,String missileId,String destination, int damage);
	
	/** Enemy is now hidden **/
	public void enemyLauncherIsHidden (String id);
	
	/** Enemy is now visible **/
	public void enemyLauncherIsVisible (String id);
	
	/**Defense event for miss interception (to missile) **/
	public void defenseMissInterceptionMissile (String whoLaunchedMeId, String id, String enemyMissileId);
	
	/**Defense event for hit interception (to missile) **/
	public void defenseHitInterceptionMissile (String whoLaunchedMeId, String id, String enemyMissileId);
	
	/**Enemy event for hit destination **/
	public void enemyHitDestination (String whoLaunchedMeId, String id, String destination, int damage);

	/**Defense event for miss interception (to Launcher) **/
	public void defenseMissInterceptionLauncher (String whoLaunchedMeId,String Type, String id, String enemyLauncherId);
	
	/**Defense event for hit interception (to Launcher) **/
	public void defenseHitInterceptionLauncher (String whoLaunchedMeId,String Type, String id, String enemyLauncherId);
}
