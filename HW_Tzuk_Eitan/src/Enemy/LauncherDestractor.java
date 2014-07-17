package Enemy;

import java.util.Comparator;

/** plane or ship**/
public class LauncherDestractor extends Munitions {
	private String type; //plane or ship
	
	public LauncherDestractor(String type,String id){
		super(id, new MissileComparator());
		this.type = type;	
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean launchMissile() {
		// TODO Auto-generated method stub
		//crazy
		return false;
	}

}

class MissileComparator implements Comparator<MissileInterface>{
	@Override
	public int compare(MissileInterface o1, MissileInterface o2) {
		if (o1 instanceof EnemyMissile)
			return ((EnemyMissile)o2).getLaunchTime() - ((EnemyMissile)o1).getLaunchTime();
		
		else
			if (o1 instanceof DefenceMissile)
				return ((DefenceMissile)o2).getDestructTime() - ((DefenceMissile)o1).getDestructTime();
		
		return 0;
	}
	
}
