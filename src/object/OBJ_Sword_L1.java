package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_L1 extends Entity {

	public OBJ_Sword_L1(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "L-1 Sword";
		down1 = setup("/objects/l1-sword");
		attackValue = 1;
		hitbox.width = (gp.tileSize * 2) - (gp.tileSize/4);
		hitbox.height = (gp.tileSize * 2) - (gp.tileSize/4);
		description = " "+name+"\n"
				+ "An old sword.";
		
	}

}
