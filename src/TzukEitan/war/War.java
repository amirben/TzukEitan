package TzukEitan.war;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import TzukEitan.launchers.EnemyLauncher;
import TzukEitan.launchers.IronDome;
import TzukEitan.launchers.LauncherDestractor;
import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.EnemyMissile;
import TzukEitan.utils.IdGenerator;

public class War extends Thread{
	
	private ArrayList<IronDome> ironDomeArr = new ArrayList<IronDome>();
	private ArrayList<LauncherDestractor> launcherDestractorArr = new ArrayList<LauncherDestractor>();
	private ArrayList<EnemyLauncher> enemyLauncherArr = new ArrayList<EnemyLauncher>();
	private boolean isRunning = true;
	private WarStatistics statistics;
	private List<WarEventListener> allListeners;
	private String[] targetCities = {"Sderot", "Ofakim", "Beer-Sheva", "Netivot", "Tel-Aviv", "Re'ut"};
	
	public War(){
		allListeners = new LinkedList<WarEventListener>(); 
		statistics = new WarStatistics();
	}
	
	public void run() {
		fireWarHasBeenStarted();
		
		while(isRunning){
			fireWaitForOrder();	
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//TODO finish thread properly
		//TODO THROW EVENT OF START AND STOP WAR
		synchronized (this) {
			stopAllMunitions();
			fireWarHasBeenFinished();
		}
	}
	


	private void stopAllMunitions() {
		for (EnemyLauncher el : enemyLauncherArr)
			el.stopEnemyLaucher();
		
		for (LauncherDestractor ld : launcherDestractorArr)
			ld.stopRunningDestractor();
		
		for (IronDome ironDome : ironDomeArr)
			ironDome.stopRunningIronDome();	
	}
	
	public void finishWar(){
		isRunning = false;
	}
	
	public WarStatistics getStatistics(){
		return statistics;
	}
	
	public String[] getAllDuringFlyMissilesIds(){
		ArrayList<String> missileIds = new ArrayList<>(enemyLauncherArr.size());
		
		for(EnemyLauncher el: enemyLauncherArr){
			if(el.getCurrentMissile() != null)
				missileIds.add(el.getCurrentMissile().getMissileId());
		}
		return (String[]) missileIds.toArray();
	}
	
	private void interceptGivenMissile(String missileId, IronDome ironDome){
		EnemyMissile missileToDestroy;
		for(EnemyLauncher el: enemyLauncherArr){
			missileToDestroy = el.getCurrentMissile();
			
			if(missileToDestroy != null && missileToDestroy.getMissileId().equals(missileId)){
				ironDome.setMissileToDestroy(missileToDestroy);
				ironDome.notify();
			}
		}
	}
	
	public synchronized void interceptGivenMissile(String missileId){
		IronDome ironDome = findFreeIronDome();
		if (ironDome == null){
			fireNoSuchObject("Iron Dome");
		}
		else
			interceptGivenMissile(missileId, ironDome);
		
	}
	
	//Use for xml
	public synchronized void interceptGivenMissile (String ironDomeID, String missileId){
		for(IronDome ironDome : ironDomeArr)
			if(ironDome.getIronDomeId().equals(ironDomeID) && !ironDome.getIsBusy())
				interceptGivenMissile(missileId, ironDome);
			else{
				fireNoSuchObject("Iron Dome");
			}
	}
	
	private void interceptGivenLauncher(String launcherId, LauncherDestractor destructor){
		for(EnemyLauncher el: enemyLauncherArr){
			if(el.getLauncherId().equals(launcherId) && el.isAlive()){
					destructor.setEnemyLauncherToDestroy(el);
					destructor.notify();
			}
		}
	}

	public synchronized void interceptGivenLauncher(String destructorId, String launcherId) {
		for(LauncherDestractor ld : launcherDestractorArr)
			if(ld.getDestructorId().equals(destructorId) && !ld.getIsBusy())
				interceptGivenLauncher(launcherId, ld);
			else{
				switch (launcherId.charAt(0)){
					case 'P': fireNoSuchObject("plane");
						break;
					case 'S': fireNoSuchObject("ship");
						break;
				}
			}
		
	}
	
	public synchronized void interceptGivenLauncher(String launcherId) {
		LauncherDestractor ld = findFreeDestructor();
		if(ld == null){
			switch (launcherId.charAt(0)){
			case 'P': fireNoSuchObject("plane");
				break;
			case 'S': fireNoSuchObject("ship");
				break;
		}
		}
		else
			interceptGivenLauncher(launcherId, ld);
		
	}
	
	public IronDome findFreeIronDome(){
		for(IronDome ironDome : ironDomeArr){
			if(!ironDome.getIsBusy())
				return ironDome;
		}
		
		return null;
	}
	
	public LauncherDestractor findFreeDestructor(){
		for(LauncherDestractor ld : launcherDestractorArr){
			if(!ld.getIsBusy())
				return ld;
		}
		return null;
	}
	
	public void registerListenerts(WarControl control){
		for(IronDome iron: ironDomeArr)
			iron.registerListeners(control);
		
		for(LauncherDestractor launcherDestractor: launcherDestractorArr)
			launcherDestractor.registerListeners(control);
		
		for(EnemyLauncher EnemyLauncher: enemyLauncherArr)
			EnemyLauncher.registerListeners(control);
		
		allListeners.add(control);
	}

	public String[] getAllVisibleLaunchersIds() {
		ArrayList<String> visibleIds = new ArrayList<>(enemyLauncherArr.size()); 
		for(EnemyLauncher el : enemyLauncherArr){
			if(el.isAlive() && !el.getIsHidden())
				visibleIds.add(el.getLauncherId());
		}
		
		return (String[])visibleIds.toArray();
	}
	
	public String[] getAllLaunchersIds(){
		ArrayList<String> visibleIds = new ArrayList<>(enemyLauncherArr.size()); 
		
		for(EnemyLauncher el : enemyLauncherArr){
			if(el.isAlive())
				visibleIds.add(el.getLauncherId());
		}
		
		return (String[])visibleIds.toArray();
	}

	public void launchEnemyMissile(String launcherId, String destination, int damage) {
		for(EnemyLauncher el : enemyLauncherArr){
			//Check if there is enemy launcher with given id
			if(el.getLauncherId().equals(launcherId) && el.isAlive()){
				//Check if launcher is not in use
				if (el.getCurrentMissile() == null){
					el.setMissileInfo(destination, damage);
					el.notify();
				}
			}
		}
	}

	public String addEnemyLauncher(String launcherId, boolean isHidden) {
		EnemyLauncher launcher = new EnemyLauncher(launcherId, isHidden, statistics);
		launcher.registerListeners(allListeners.get(0));
		
		enemyLauncherArr.add(launcher);
		
		return launcherId;
	}
	
	public String addEnemyLauncher(){
		String id = IdGenerator.enemyLauncherIdGenerator();
		boolean isHidden = Math.random()<0.5;
		
		addEnemyLauncher(id, isHidden);
		
		return id;
	}

	public String addIronDome(String id) {
		IronDome ironDome = new IronDome(id, statistics);
		ironDome.registerListeners(allListeners.get(0));
		
		ironDomeArr.add(ironDome);
		
		return id;
	}
	
	public String addIronDome() {
		String id = IdGenerator.ironDomeIdGenerator();
		addIronDome(id);
		
		return id;
	}

	public String addDefenseLauncherDestractor(String type) {
		String id = IdGenerator.defenseLauncherDestractorIdGenerator(type.charAt(0));
		LauncherDestractor destructor = new LauncherDestractor(type, id, statistics);
		destructor.registerListeners(allListeners.get(0));
		launcherDestractorArr.add(destructor);
		
		return id;
	}
	
	public String[] getAllTargetCities(){
		return targetCities;
	}
	
	private void fireWarHasBeenFinished() {
		for (WarEventListener l : allListeners)
			l.warHasBeenFinished();
	}
	

	private void fireWarHasBeenStarted() {
		for (WarEventListener l : allListeners)
			l.warHasBeenStarted();
	}
	
	private void fireNoSuchObject(String type){
		for (WarEventListener l : allListeners)
			l.noSuchObject(type);
	}
	
	private void fireWaitForOrder() {
		for (WarEventListener l : allListeners)
			l.waitForOrder();
	}


}
