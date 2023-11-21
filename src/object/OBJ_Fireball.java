package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage() {
		up1 = setup("/projectile/fireball_up1");
		up2 = setup("/projectile/fireball_up2");
		down1 = setup("/projectile/fireball_down1");
		down2 = setup("/projectile/fireball_down2");
		left1 = setup("/projectile/fireball_left1");
		left2 = setup("/projectile/fireball_left2");
		right1 = setup("/projectile/fireball_right1");
		right2 = setup("/projectile/fireball_right2");
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
}
