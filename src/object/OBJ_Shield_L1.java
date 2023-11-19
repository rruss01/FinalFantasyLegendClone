package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_L1 extends Entity {

	public OBJ_Shield_L1(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Wood Shield";
		down1 = setup("/objects/l1-shield");
		defenseValue = 1;
		description = " "+name+"\n"
				+ "It ain't much, but\n"
				+ "it'll do.";
	}

}
