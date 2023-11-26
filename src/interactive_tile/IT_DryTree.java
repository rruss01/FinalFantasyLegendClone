package interactive_tile;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;
import tiles.IT_Stump;

public class IT_DryTree extends InteractiveTile {

	GamePanel gp;
	
	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/interactive_tiles/dry_tree");
		destructible = true;
		life = 3;
	}

	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		if(entity.currentWeapon.type == type_axe) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	public void playSE() {
		//gp.playSE();
	}
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Stump(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	public Color getParticleColor() {
		Color color = new Color(0, 0, 0);
		return color;
	}
	public int getParticleSize() {
		int size = 6;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
