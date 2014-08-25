package TzukEitan.launchers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenseMissile;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.utils.IdGenerator;
import TzukEitan.utils.Utils;
import TzukEitan.utils.WarFormater;
import TzukEitan.war.WarStatistics;

public class IronDome extends Thread implements Munitions {
	private List<WarEventListener> allListeners;

	private String id;
	private boolean isRunning = true;
	private boolean isBusy = false;
	private EnemyMissile toDestroy;
	private WarStatistics statistics;
	private DefenseMissile currentMissile;

	private static Logger theLogger = Logger.getLogger("warLogger");
	private FileHandler ironDomeHandler;

	public IronDome(String id, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>();

		this.statistics = statistics;
		this.id = id;

		addLoggerHandler();
	}

	public void run() {
		// this thread will be alive until the war is over
		while (isRunning) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					stopRunning();
					break;
				}
			}
			
			// update that this iron-dome is in use
			isBusy = true;
			try {
				launchMissile();
				
				// update that this iron dome is not in use
				isBusy = false;
			} catch (InterruptedException e) {
				stopRunning();
				break;
			}

			// update that is no missile in this iron-dome
			currentMissile = null;

		}// while

		// close the handler of the logger
		ironDomeHandler.close();
	}// run

	public void addLoggerHandler() {
		try {
			ironDomeHandler = new FileHandler("log\\IronDome" + id
					+ "Logger.xml", false);

			ironDomeHandler.setFilter(new Filter() {
				public boolean isLoggable(LogRecord rec) {
					if (rec.getMessage().contains(id))
						return true;
					return false;
				}
			});// add filter

			ironDomeHandler.setFormatter(new WarFormater());

			theLogger.addHandler(ironDomeHandler);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// set the next target of this iron dome, called from the war
	public void setMissileToDestroy(EnemyMissile toDestroy) {
		this.toDestroy = toDestroy;
	}

	// Launch missile with given parameters
	public void launchMissile() throws InterruptedException {	
		createMissile();
		
		// sleep for launch time
		sleep(Utils.LAUNCH_DURATION);
		
		// check if the target is still alive
		if (toDestroy != null && toDestroy.isAlive()) {
			// throw event
			fireLaunchMissileEvent(currentMissile.getMissileId());
	
			// Start missile and wait until he will finish to be able
			// to shoot anther one
			
			currentMissile.start();
			currentMissile.join();
		}
		else{
			fireMissileNotExist(toDestroy.getMissileId());
		}
	}

	private void fireMissileNotExist(String missileId) {
		for (WarEventListener l : allListeners)
			l.missileNotExist(getIronDomeId(), missileId);;
	}

	public void createMissile() {
		// generate missile id
		String missieId = IdGenerator.defensMissileIdGenerator();

		// create new missile
		currentMissile = new DefenseMissile(missieId, toDestroy, id, statistics);

		// register listeners
		for (WarEventListener l : allListeners)
			currentMissile.registerListeners(l);
	}

	public DefenseMissile getCurrentMissile() {
		return currentMissile;
	}

	// Event
	private void fireLaunchMissileEvent(String missileId) {
		for (WarEventListener l : allListeners) {
			l.defenseLaunchMissile(id, missileId, toDestroy.getMissileId());
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}

	// check if can shoot from this current iron dome
	public boolean getIsBusy() {
		return isBusy;
	}

	public String getIronDomeId() {
		return id;
	}

	// use for end the thread
	@Override
	public void stopRunning() {
		currentMissile = null;
		toDestroy = null;
		isRunning = false;
	}
	
}
