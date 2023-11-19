package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion extends Entity {

	GamePanel gp;
	int value = 5;
	
	public OBJ_Potion(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_consumable;
		name = "Health Potion";
		down1 = setup("/objects/potion");
		description = " "+name+"\n"
				+ "Heathful and tastes\n"
				+ "of elderberries.\n"
				+ "heals 5 hp.";
	}
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " + name + "!\n" +
				"Your life has been recovered by " + value +".";
		entity.life += value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(4);
	}

}
