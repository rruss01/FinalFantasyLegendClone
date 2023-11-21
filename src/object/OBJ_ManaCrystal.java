package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

	GamePanel gp;
	
	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickup_only;
		value = 1;
		name = "Mana Crystal";
		down1 = image = setup("/objects/mana");
		image = setup("/objects/mana");
		image2 = setup("/objects/mana_empty");
	}

	public void use(Entity entity) {
		gp.playSE(13);
		gp.ui.addMessage("Mana +" +value);
		entity.mana += value;
	}
	
}
