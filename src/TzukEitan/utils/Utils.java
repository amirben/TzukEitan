package TzukEitan.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
	public static final int SECOND = 1000;
	public static final int FLY_TIME = 3;
	public static final int LAUNCH_DURATION = 1000;
	private static final double DESTRACOTR_SUCCES_RATE = 0.95;
	private static final double SUCCES_RATE = 0.7;
	private static final double IS_HIDDEN_RATE = 0.5;

	// create local date
	public static String getCurrentTime() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		return ldt.format(dtf);
	}

	// delete all old logs in the folder
	public static void deleteFolder() {
		File file = new File("log");
		File[] files = file.listFiles();

		if (files != null){
			for (File f : files){
				f.delete();
			}
		}
	}

	/** randomize the success rate of missile **/
	public static boolean randomSuccesRate() {
		return Math.random() < SUCCES_RATE;
	}

	/** randomize the success rate of dedtructor **/
	public static boolean randomDestractorSucces() {
		return Math.random() < DESTRACOTR_SUCCES_RATE;
	}

	/** randomize if launcher will be hidden or not **/
	public static boolean randomIsHidden() {
		return Math.random() < IS_HIDDEN_RATE;
	}
}
