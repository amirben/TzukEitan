package TzukEitan.Enemy;

import java.util.ArrayList;
import java.util.Scanner;

import TzukEitan.launchers.EnemyLauncher;
import TzukEitan.launchers.IronDome;
import TzukEitan.launchers.LauncherDestractor;
import TzukEitan.missiles.DefenceMissile;
import TzukEitan.missiles.EnemyMissile;

public class War implements Runnable {
	private StringBuilder menu = new StringBuilder(1000);
	private Scanner input = new Scanner (System.in);
	private ArrayList<IronDome> ironDomeArr = new ArrayList<IronDome>();
	private ArrayList<LauncherDestractor> launcherDestractorArr = new ArrayList<LauncherDestractor>();
	private ArrayList<EnemyLauncher> EnemyLauncherArr = new ArrayList<EnemyLauncher>();

	public War(){
		createMenu();
	}
	
	@Override
	public void run() {
		//while(true)
		manageMenu();
		
	}
	
	public void createMenu(){
		menu.append("choose one option: \n\n");
		menu.append("1. Add Munition to Intercept launchers.\n");
		menu.append("2. Add Munition to Intercept missile.\n");
		menu.append("3. Add launcher.\n");
		menu.append("4. Launch a missile.\n");
		menu.append("5. Intercept a launcher.\n");
		menu.append("6. Intercept a missile.\n");
		menu.append("7. Show statistics.\n");
		menu.append("8. End the war and show statistics.\n");
	}
	
	public void manageMenu(){
		showMenu();
		int choise = input.nextInt();
		manageChoise(choise);
	}
	
	public void manageChoise(int choise){
		switch(choise){
		
		case 1:  newInterceptLaunch(); break;//new plane or ship
		
		case 2:  newIronDome(); break;
		
		case 3:  newLauncher(); break;
		
		case 4:  launchMissile(); break;
		
		case 5:  interceptLauncher(); break;

		case 6:  interceptMissile(); break;
		
		case 7:  showStatistics(); break;
		
		case 8:  endWar(); break;
		}
	}
	


	public void showMenu(){
		System.out.println(menu.toString());
	}
	
	//new plane or ship
	public void newInterceptLaunch(){
		int missileNum;
		String type;
		System.out.println("Please enter the type (plane/ship):");
		type = input.next();
		LauncherDestractor launcherDestractor = new LauncherDestractor(type,idGenerator(type.toUpperCase().charAt(0)));
		
		System.out.println("How many missiles do you want to add?");
		missileNum = input.nextInt();
		
		for(int i=0 ; i<missileNum ; i++){
			DefenceMissile tempMissile = new DefenceMissile();
			launcherDestractor.addMissile(tempMissile);
		}
		
		
	}
	//Generate id for the munitions cons't 
	public String idGenerator(char ch){
		//TODO generate actual id
		int num = 0;
		return "D"+num;
	}
	
	/**Create new Iron Dome**/
	public void newIronDome(){
		int missileNum;
		IronDome ironDome = new IronDome(idGenerator('D'), new MissileComparator());
		System.out.println("How many missiles do you want to add?");
		missileNum = input.nextInt();
		for(int i=0 ; i<missileNum ; i++){
			//what properties should a missile has?
			DefenceMissile tempMissile = new DefenceMissile();
			ironDome.addMissile(tempMissile);
		}
	}
	
	/**Create new enemy launcher**/
	public void newLauncher(){
		int missileNum;
		EnemyLauncher enemyLauncher = new EnemyLauncher(idGenerator('L'), new MissileComparator());
		System.out.println("How many missiles do you want to add?");
		missileNum = input.nextInt();
		for(int i=0 ; i<missileNum ; i++){
			
			EnemyMissile tempMissile = new EnemyMissile();//TODO
			enemyLauncher.addMissile(tempMissile);
		}
	}
	
	
	/**enemy fire a misslie**/
	public void launchMissile(){
		
		//TODO run on the EnemyLaunchers and find a free one
		
	
	}

	
	public void interceptLauncher() {
		
		//TODO
		//looking for aviallble plane / ship o fire the launcher
		
		//looking for the first not hidden launcher
	}
	
	public void interceptMissile(){
		//TODO find an avialablle dome to intecept one of the missile in the sky
		
		EnemyMissye toDetl
		irondome.set(toD)
		ironDome.notid();
	}
	
	public void showStatistics(){
		//TODO statistocs
	}

	public void endWar(){
		showStatistics();
		//TODO end the war
	}


}
