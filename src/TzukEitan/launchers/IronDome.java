package TzukEitan.launchers;

import Enemy.MunitionsEvent;
import TzukEitan.missiles.DefenceMissile;
import TzukEitan.missiles.EnemyMissile;

public class IronDome extends Thread implements MunitionsEvent {
	private EnemyMissile toDestroy;
	private static int missleIdGen;
	private String id;
	
	public IronDome(String id) {
		this.id = id;
		missleIdGen = 100;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		synchronized (this) {
			try{
				//Wait until user will try to destory launcher
				wait();
				
				if (toDestroy != null)
					launchMissile();	
			}	
			//Exception is called when launcher has been hit
			catch(InterruptedException ex){
				//TODO something
			}
		}
	}

	public void setMissileToDestroy(EnemyMissile toDestroy){
		this.toDestroy = toDestroy;
	}
	
	@Override
	public boolean launchMissile() {
		String id = idGenerator();
		DefenceMissile missile = new DefenceMissile(id ,toDestroy);
		
		missile.start();
		
		return true;
	}
	
	//Generate new ID
	public String idGenerator(){
		return "M" + missleIdGen++;
	}

}
