package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	
	// counters
	public int spriteCounter = 0;
	public int standCounter = 0;
	public int iFramesCounter = 0;
	public int projectileCounter = 0;
	public int actionLockCounter = 0;
	int dyingCounter;
	int hpBarCounter;
	
	// character attributes
	public String name;
	public int speed;
	public int maxLife;
	public int maxMana;
	public int mana;
	public int ammo;
	public int life;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	// item attributes
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	public int value;
	
	// type
	public int type;
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickup_only = 7;

	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() {}
		
	public void damageReaction() {}
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
	
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -1, -1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 1, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -1, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 1, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	
	public void use(Entity entity) {}
	
	public void checkDrop() {}
	public void dropItem(Entity droppedItem) {
		
		for(int i = 0; i < gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				break; // fills only the most recent empty slot
			}
		}
	}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
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
		if(projectileCounter < 30) {
			projectileCounter++;
		}
	}
	public void damagePlayer(int attack) {
		if (gp.player.invincible == false) {
			gp.playSE(6);
			int damage = attack - gp.player.defense;
			if(damage < 0) {
				damage = 0;
			}
			gp.player.life -= damage;
			gp.player.invincible = true;
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
			
			if (type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale*life;
				if(hpBarValue < 0) { hpBarValue = 0; }
				
				// DRAWS HP BAR
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 1, screenY - 16, gp.tileSize, 10);
				
				g2.setColor(new Color(100, 100, 100));
				g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
				
				hpBarCounter++;
				
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			if (invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);
			}
			if (dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);
			// reset alpha so it's drawn correctly when the i-frames run out
			changeAlpha(g2, 1f);
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
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		if(dyingCounter <= 5) {changeAlpha(g2, 0f);} 
		if(dyingCounter > 5 && dyingCounter <= 10) {changeAlpha(g2, 1f);}
		if(dyingCounter > 10 && dyingCounter <= 15) {changeAlpha(g2, 0f);}
		if(dyingCounter > 15 && dyingCounter <= 20) {changeAlpha(g2, 1f);}
		if(dyingCounter > 20 && dyingCounter <= 25) {changeAlpha(g2, 0f);}
		if(dyingCounter > 25 && dyingCounter <= 30) {changeAlpha(g2, 1f);}
		if(dyingCounter > 30 && dyingCounter <= 35) {changeAlpha(g2, 0f);}
		if(dyingCounter > 35 && dyingCounter <= 40) {changeAlpha(g2, 1f);}
		if(dyingCounter > 40) {
			alive = false;
		}
		
	}
	public void changeAlpha(Graphics2D g2, float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
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
