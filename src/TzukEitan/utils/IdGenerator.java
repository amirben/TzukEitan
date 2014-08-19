package TzukEitan.utils;

public class IdGenerator {
	private static int ironDomeId = 201;
	private static int enemyLauncherId = 101;
	private static int defenseLauncherDestractorId = 301;
	private static int enemyMissileId = 1;
	private static int defenseMissileId = 1000;
	
	
	public static String ironDomeIdGenerator() {
		return "D" + ironDomeId++;
	}

	public static String enemyLauncherIdGenerator() {
		return "L" + enemyLauncherId++;
	}
	
	public static String defenseLauncherDestractorIdGenerator(char type){
		return  ("" + type).toUpperCase() + defenseLauncherDestractorId++;
	}

	public static String enemyMissileIdGenerator() {
		return "M" + enemyMissileId++;
	}
	
	public static String defensMissileIdGenerator() {
		return "DM" + defenseMissileId++;
	}
}
