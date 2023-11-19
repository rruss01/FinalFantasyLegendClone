package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_L2 extends Entity {

	public OBJ_Shield_L2(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Metal Shield";
		down1 = setup("/objects/l2-shield");
		defenseValue = 2;
		description = " "+name+"\n"
				+ "Heavy and durable.\n";
	}

	
}
