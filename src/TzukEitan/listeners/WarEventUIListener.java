package TzukEitan.listeners;

public interface WarEventUIListener {
	/** Stop the war, truce  and show war statistics**/
	public void finishWar();
	
	/**Show current war statistics**/
	public void showStatistics();
	
	/**Ask for current missiles in air**/
	public String[] chooseMissileToIntercept();
	
	/**Will try to intercept given missile id**/
	public void interceptGivenMissile(String id);
	
	/**Ask for current launcher that are not hidden**/
	public String[] chooseLauncherToIntercept();
	
	/**Will try to intercept given launcher id**/
	public void interceptGivenLauncher(String id);
	
	/**User will select from which launcher he would like to launch missile**/
	public String[] showAllLaunchers();
	
	/**Add missile to given launcher**/
	public void addEnemyMissile(String destination);
	
	/**Add enemy launcher**/
	public void addEnemyLauncher();
	
	/**Add defense Iron Dome**/
	public void addIronDome();
	
	/**Add plane or ship**/
	public void addDefenseLauncherDestractor(String type);
}

