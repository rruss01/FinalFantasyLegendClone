package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

	public OBJ_Rock(GamePanel gp) {
		super(gp);
		name = "Rock";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage() {
		up1 = setup("/projectile/rock_down1");
		up2 = setup("/projectile/rock_down1");
		down1 = setup("/projectile/rock_down1");
		down2 = setup("/projectile/rock_down1");
		left1 = setup("/projectile/rock_down1");
		left2 = setup("/projectile/rock_down1");
		right1 = setup("/projectile/rock_down1");
		right2 = setup("/projectile/rock_down1");
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
}
