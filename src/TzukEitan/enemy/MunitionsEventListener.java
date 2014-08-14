package TzukEitan.enemy;

public interface MunitionsEventListener {

	/** Defence Iron Dome launch interception missile **/
	public void defenceLaunchMissile (String id);
	
	/** Defence Airplane or ship launch interception missile **/
	public void defenceLaunchMissile (String id, String type);
	
	/** Enemy Launch missile **/
	public void enemyLauncherLaunchMissile (String id);
	
	/** Enemy is now hidden **/
	public void enemyLauncherIsHidden (String id);
	
	/** Enemy is now visible **/
	public void enemyLauncherIsVisible (String id);
}
