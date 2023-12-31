package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.*;

public class MON_Flame extends Entity {

	GamePanel gp;
	
	public MON_Flame(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name = "Flame";
		speed = 2;
		maxLife = 8;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 5;
		projectile = new OBJ_Rock(gp);
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 40;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/monster/flame_down1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/flame_down2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/flame_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/flame_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/flame_down1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/flame_down2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/flame_down1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/flame_down2", gp.tileSize, gp.tileSize);
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
		int i = new Random().nextInt(100)+1;
		if(i > 99 && projectile.alive == false && projectileCounter == 30) {
			
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			projectileCounter = 0;
		}
	}

	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
	public void checkDrop() {
		int i = new Random().nextInt(100)+1;
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
}
