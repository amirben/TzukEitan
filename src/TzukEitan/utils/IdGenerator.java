package TzukEitan.utils;

public class IdGenerator {
	private static int ironDomeId = 100;
	private static int enemyLauncherId = 100;
	private static int defenseLauncherDestractorId = 100;
	private static int missileId = 100;
	
	
	public static String ironDomeIdGenerator() {
		return "D" + ironDomeId++;
	}

	public static String enemyLauncherIdGenerator() {
		return "L" + enemyLauncherId++;
	}
	
	public static String defenseLauncherDestractorIdGenerator(String type){
		return type + defenseLauncherDestractorId++;
	}

	public static String missileIdGenerator() {
		return "M" + missileId++;
	}
}
