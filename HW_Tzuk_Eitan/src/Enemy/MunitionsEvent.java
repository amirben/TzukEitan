package Enemy;

import java.util.Stack;


public interface MunitionsEvent {
//	private String id;
//	private Stack <MissileInterface> missileStack = new Stack<>();
//
//	
//	public Munitions(String id){
//		this.id = id;
//	}
//	
//	public String getId(){
//		return id;
//	}
	
//	public Stack <MissileInterface> getMissileStack(){
//		return missileStack;
//	}
//	
//	public void addMissile(MissileInterface missile){
//		try{
//			missileStack.push(missile);
//		}catch(NullPointerException ex){
//			System.out.println("Missile queue is not exist");
//		};
//	}
//	
	/** Occur when launcher want to fire missile **/
	public boolean launchMissile();
	
	
}
