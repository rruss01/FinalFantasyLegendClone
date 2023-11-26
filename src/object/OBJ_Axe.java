package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		type = type_axe;
		name = "Axe";
		down1 = setup("/objects/axe");
		attackValue = 2;
		hitbox.width = (gp.tileSize * 2) - (gp.tileSize/3);
		hitbox.height = (gp.tileSize * 2) - (gp.tileSize/3);
		
		description = " "+name+"\n"
				+ "Old but can still\n"
				+ "cut down trees.";
	}

}
