package TzukEitan.missiles;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventListener;
import TzukEitan.launchers.EnemyLauncher;
import TzukEitan.utils.Utils;
import TzukEitan.war.WarStatistics;

/** Missile for Plane or Ship **/
public class DefenseDestructorMissile extends Thread {
	private List<WarEventListener> allListeners;

	private String id;
	private String whoLaunchedMeId;
	private String whoLaunchedMeType;
	private EnemyLauncher LauncherToDestroy;
	private WarStatistics statistics;

	public DefenseDestructorMissile(String id, EnemyLauncher LauncherToDestroy,
			String whoLunchedMeId, String whoLaunchedMeType,
			WarStatistics statistics) {

		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.LauncherToDestroy = LauncherToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.whoLaunchedMeType = whoLaunchedMeType;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (this) {
			if (LauncherToDestroy.isAlive() && !LauncherToDestroy.getIsHidden()
					&& Utils.randomDestractorSucces()) {
				// Check if the launcher is hidden or not
				fireHitEvent();
				LauncherToDestroy.interrupt();

			} else {
				fireMissEvent();
			}// if & else
		}// synchronized
	}

	// Event
	private void fireHitEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionLauncher(whoLaunchedMeId,
					whoLaunchedMeType, id, LauncherToDestroy.getLauncherId());
		}

		// update statistics
		statistics.increaseNumOfLauncherDestroyed();
	}

	// Event
	private void fireMissEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionLauncher(whoLaunchedMeId,
					whoLaunchedMeType, id, LauncherToDestroy.getLauncherId());
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}

	public String getMissileId() {
		return id;
	}

}
