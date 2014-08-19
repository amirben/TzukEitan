package TzukEitan.war;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import TzukEitan.launchers.EnemyLauncher;
import TzukEitan.launchers.IronDome;
import TzukEitan.launchers.LauncherDestractor;
import TzukEitan.listeners.WarEventListener;
import TzukEitan.missiles.DefenceMissile;
import TzukEitan.missiles.EnemyMissile;

public class War implements Runnable {
	
	private ArrayList<IronDome> ironDomeArr = new ArrayList<IronDome>();
	private ArrayList<LauncherDestractor> launcherDestractorArr = new ArrayList<LauncherDestractor>();
	private ArrayList<EnemyLauncher> enemyLauncherArr = new ArrayList<EnemyLauncher>();
	private boolean isRunning = true;
	private WarStatistics statistics;
	private List<WarEventListener> allListeners;
	
	public War(){
		allListeners = new LinkedList<WarEventListener>(); 
		statistics = new WarStatistics();
	}
	
	public void run() {
		while(isRunning){
					
		}
		//TODO finish thread properly
		//TODO THROW EVENT OF START AND STOP WAR
		
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
	
	public void interceptGivenMissile(String missileId){
		EnemyMissile missileToDestroy;
		for(EnemyLauncher el: enemyLauncherArr){
			missileToDestroy = el.getCurrentMissile();
			
			if(missileToDestroy != null && missileToDestroy.getMissileId().equals(missileId)){
				IronDome ironDome = findFreeIronDome();
				if(ironDome == null)
					//TODO throw event no free iron dome
			
				ironDome.setMissileToDestroy(missileToDestroy);
				ironDome.notify();
			}
		}
	}
	
	public void interceptGivenLauncher(String launcherId){
		for(EnemyLauncher el: enemyLauncherArr){
			if(el.getLauncherId().equals(launcherId) && el.isAlive()){
				LauncherDestractor ld = findFreeDestructor();
				if(ld == null)
					//TODO throw event no free destructor
				ld.setEnemyLauncherToDestroy(el);
				ld.notify();
			}
		}
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
			if(el.getLauncherId().equals(launcherId) && el.isAlive()){
				if (el.getCurrentMissile() == null){
					el.setMissileInfo(destination, damage);
					el.notify();
				}
			}
		}
	}

	public void addEnemyLauncher(String launcherId, boolean isHidden) {
		EnemyLauncher launcher = new EnemyLauncher(launcherId, isHidden, statistics);
		launcher.registerListeners(allListeners.get(0));
		enemyLauncherArr.add(launcher);
	}
	
	public void addEnemyLauncher(){
		String id = Utils.enemyLauncherIdGenerator();
		boolean isHidden = Math.random()<0.5;
		addEnemyLauncher(id, isHidden);
	}

	public void addIronDome(String id) {
		IronDome ironDome = new IronDome(id, statistics);
		ironDome.registerListeners(allListeners.get(0));
		ironDomeArr.add(ironDome);
	}
	
	public void addIronDome() {
		String id = Utils.IronDomeIdGenerator();
		addIronDome(id);
	}

	public void addDefenseLauncherDestractor(String type) {
		String id = Utils.defenceLauncherDestructorIdGenerator(type.charAt(0));
		LauncherDestractor destructor = new LauncherDestractor(type, id, statistics);
		destructor.registerListeners(allListeners.get(0));
		
		launcherDestractorArr.add(destructor);
		
	}

}
