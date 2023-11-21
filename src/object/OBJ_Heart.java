package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		type = type_pickup_only;
		name = "Heart";
		value = 2;
		down1 = setup("/objects/heart_full");
		image = setup("/objects/heart_full");
		image2 = setup("/objects/heart_half");
		image3 = setup("/objects/heart_blank");
	}
	
	public void use(Entity entity) {
		gp.playSE(13);
		gp.ui.addMessage("Life +" + value);
		entity.life += value;
	}
		
}