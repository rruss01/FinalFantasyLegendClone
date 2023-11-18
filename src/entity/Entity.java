package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, 
	attackDown1, attackDown2, attackDown3, attackDown4, 
	attackLeft1, attackLeft2, attackLeft3, attackLeft4, 
	attackRight1, attackRight2, attackRight3, attackRight4;
	public BufferedImage image, image2, image3;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle hitbox = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	String dialogues[] = new String[20];
	
	// world
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean invincible = false;
	boolean attacking = false;
	
	// counters
	public int spriteCounter = 0;
	public int standCounter = 0;
	public int iFramesCounter = 0;
	public int actionLockCounter = 0;
	
	// entity attributes
	public int type; // 0 = player, 1 = npc, 2 = monster
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	
	public void setAction() {
		
	}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		// pretty sure this is temporary, as all monsters won't do the same damage in the final product
		if(this.type == 2 && contactPlayer == true) {
			if (gp.player.invincible == false) {
				gp.playSE(6);
				gp.player.life -= 1;
				gp.player.invincible = true;
			}
		}
		
		// npc can move if collision is false
		if (collisionOn == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		spriteCounter++;
					
		if (spriteCounter > 10) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		if (invincible == true) {
			iFramesCounter++;
			if (iFramesCounter > 30) {
				invincible = false;
				iFramesCounter = 0;
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX; // calculates position relative to player, who controls the camera
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
				break;
			case "down":
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
				break;
			case "left":
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
				break;
			case "right":
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;} 
				break;
			}
			
			if (invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			// reset alpha so it's drawn correctly when the i-frames run out
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
