package Enemy;

//TODO logger & Syso
import java.util.Stack;


public class EnemyLauncher extends Munitions{
	
	private boolean beenHit = false;
	private boolean isHidden;
	private boolean firstHiddenState;
	//private int region;
	
	public EnemyLauncher(String id,boolean isHidden) {
		super(id);
		this.isHidden = isHidden;
		firstHiddenState = isHidden;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(!beenHit){
			synchronized (this) {
				try{
					wait();
					isHidden = false;
					launchMissile();	
				}	
				//Exception is called when launcher has been hit
				catch(InterruptedException ex){
					hasBeenHit();
				}
			}	
		}
	}

	public void hasBeenHit(){
		System.out.println("Launcher " + getId() + "has been hit");
		beenHit = true;
		//TODO logger
	}
	
	public boolean launchMissile() {
		//TODO logger
		Stack missileStack = getMissileStack();
		if(!missileStack.isEmpty()){
			EnemyMissile missile = (EnemyMissile)missileStack.pop();
			missile.start();
			try {
				Thread.sleep((long)(500 + 1000 * (Math.random() * 10)));
				isHidden = firstHiddenState;
				missile.join();
			} catch (InterruptedException e) {
				//if we are here then while the missile is in the air we have been hit
				hasBeenHit();
				return false;
			}
			return true;
		}
		return false;
	}

}
