package TzukEitan.launchers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenseDestructorMissile;
import TzukEitan.utils.IdGenerator;
import TzukEitan.utils.Utils;
import TzukEitan.utils.WarFormater;
import TzukEitan.war.WarStatistics;

/** Plane or Ship **/
public class LauncherDestructor extends Thread{
	private List<WarEventListener> allListeners;

	private String id;
	private String type; // plane or ship
	private boolean isRunning = true;
	private boolean isBusy = false;
	private EnemyLauncher toDestroy;
	private WarStatistics statistics;
	private DefenseDestructorMissile currentMissile;

	private static Logger theLogger = Logger.getLogger("warLogger");
	private FileHandler launcherDestractorHandler;

	public LauncherDestructor(String type, String id, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.type = type;
		this.statistics = statistics;

		addLoggerHandler();
	}

	public void run() {

		while (isRunning) {
			synchronized (this) {
				try {
					// Wait until user try to destroy missile
					wait();

					// with this boolean you can see if the launcher is
					// available to use
					isBusy = true;

					// checking if the missile you would like to destroy is aive
					// (as a thread)
					// is not null (if there is any missile) and if he isn't
					// hidden
					if (toDestroy != null && toDestroy.isAlive()
							&& !toDestroy.getIsHidden()) {
						launchMissile();
					} else {
						fireLauncherIsHiddenEvent(toDestroy.getLauncherId());
					}
				} catch (InterruptedException ex) {
					// used for end the war
				}
			}// synchronized

			// update that the launcher is not in use
			isBusy = false;

		}// while

		// close the handler of the logger
		launcherDestractorHandler.close();
	}// run

	private void addLoggerHandler() {
		try {
			launcherDestractorHandler = new FileHandler("log\\" + type + ""
					+ id + "Logger.xml", false);

			launcherDestractorHandler.setFilter(new Filter() {
				public boolean isLoggable(LogRecord rec) {
					if (rec.getMessage().contains(id))
						return true;
					return false;
				}
			});// add filter

			launcherDestractorHandler.setFormatter(new WarFormater());

			theLogger.addHandler(launcherDestractorHandler);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// set the next target of this launcher destructor, called from the war
	public void setEnemyLauncherToDestroy(EnemyLauncher toDestroy) {
		this.toDestroy = toDestroy;
	}

	public void launchMissile() throws InterruptedException {
		// create launcher destructor missile
		createMissile();

		// sleep for launch time;
		sleep(Utils.LAUNCH_DURATION);

		// Throw event
		fireLaunchMissileEvent(currentMissile.getMissileId());

		// Start missile and wait until he will finish to be able
		// to shoot anther one
		currentMissile.start();
		currentMissile.join();
	}

	public void createMissile() {
		// generate missile id
		String MissileId = IdGenerator
				.defenseDestractorLauncherMissileIdGenerator(type.charAt(0));

		// create new missile
		currentMissile = new DefenseDestructorMissile(MissileId, toDestroy, id,
				type, statistics);

		// register all listeners
		for (WarEventListener l : allListeners)
			currentMissile.registerListeners(l);
	}

	public DefenseDestructorMissile getCurrentMissile() {
		return currentMissile;
	}

	// Event
	private void fireLaunchMissileEvent(String missileId) {
		for (WarEventListener l : allListeners) {
			l.defenseLaunchMissile(id, type, missileId,
					toDestroy.getLauncherId());
		}
	}

	// Event
	private void fireLauncherIsHiddenEvent(String launcherId) {
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionHiddenLauncher(id, type, launcherId);
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}

	// use for end the thread
	public void stopRunningDestractor() {
		isRunning = false;
	}

	// check if can shoot from this current launcher destructor
	public boolean getIsBusy() {
		return isBusy;
	}

	public String getDestructorId() {
		return id;
	}
}
