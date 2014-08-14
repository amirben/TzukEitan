package TzukEitan.enemy;

public interface MissileEventListener{
	
	/**Defence event for launch missile **/
	public void defenceLaunchMissile (String id, String enemyMissileId);
	
	/**Defence event for miss interception **/
	public void defenceMissInterception (String id, String enemyMissileId);
	
	/**Defence event for hit interception **/
	public void defenceHitInterception (String id, String enemyMissileId);
	
	/**Enemy event for launch missile **/
	public void enemyLaunchMissile (String id, String destination);
	
	/**Defence event for hit destination **/
	public void enemyHitDestination (String id, String destination, int damage);
	
	/**Defence event for interception by defence missile **/
	public void enemyInterception (String id, String destination);
}
