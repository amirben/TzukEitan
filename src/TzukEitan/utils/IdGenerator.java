package TzukEitan.utils;

public class IdGenerator {
	private static int ironDomeId = 203;
	private static int enemyLauncherId = 103;
	private static int defenseLauncherDestractorId = 303;
	private static int enemyMissileId = 5;
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
