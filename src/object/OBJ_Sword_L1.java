package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_L1 extends Entity {

	public OBJ_Sword_L1(GamePanel gp) {
		super(gp);
		
		name = "Normal Sword";
		down1 = setup("/objects/l1-sword");
		attackValue = 1;

	}

}
