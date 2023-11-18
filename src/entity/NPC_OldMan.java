package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;	
		
		getImage();
		setDialogue();
	}
	public void getImage() {
	
		up1 = setup("/npc/bogard_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/bogard_up2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/bogard_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/bogard_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/bogard_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/bogard_left2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/bogard_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/bogard_right2", gp.tileSize, gp.tileSize);
	}
	public void setDialogue() {
		dialogues[0] = "Hello, lad.";
		dialogues[1] = "In search of the mystical \ntreasure, I presume?";
		dialogues[2] = "I used to be a great wizard, but \nnow I'm old and my back hurts.";
		dialogues[3] = "There's a fountain just north of \nhere. It'll heal you should you \nget hurt.";
	}
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // pick number between 1-100
			
			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	public void speak() {
		super.speak();
	}
		
}
