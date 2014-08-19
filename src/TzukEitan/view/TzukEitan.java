package TzukEitan.view;

import TzukEitan.war.War;
import TzukEitan.war.WarControl;

public class TzukEitan {

	public static void main(String[] args) {
		ConsoleView view = new ConsoleView();
		War warModel = new War();
		
		WarControl warControl = new WarControl(warModel, view);
				
		warModel.start();

	}

}
