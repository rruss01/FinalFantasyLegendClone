package entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.*;

public class Player extends Entity {

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 30;
		solidArea.height = 30;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		// player stats
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;
		dexterity = 1; // determines defense
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_L1(gp);
		currentShield = new OBJ_Shield_L1(gp);
		attack = getAttack(); // strength * weapon
		defense = getDefense(); // dexterity * shield
	}
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
	}
	public int getAttack() {
		hitbox = currentWeapon.hitbox;
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/up1");
		up2 = setup("/player/up2");
		down1 = setup("/player/down1");
		down2 = setup("/player/down2");
		left1 = setup("/player/left1");
		left2 = setup("/player/left2");
		right1 = setup("/player/right1");
		right2 = setup("/player/right2");
	}
	public void getPlayerAttackImage() {
		if (currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/slash_up0", gp.tileSize * 2, gp.tileSize * 3);
			attackUp2 = setup("/player/slash_up1", gp.tileSize * 2, gp.tileSize * 3);
			attackUp3 = setup("/player/slash_up2", gp.tileSize * 2, gp.tileSize * 3);
			attackUp4 = setup("/player/slash_up3", gp.tileSize * 2, gp.tileSize * 3);
			attackDown1 = setup("/player/slash_down0", gp.tileSize * 2, gp.tileSize * 3);
			attackDown2 = setup("/player/slash_down1", gp.tileSize * 2, gp.tileSize * 3);
			attackDown3 = setup("/player/slash_down2", gp.tileSize * 2, gp.tileSize * 3);
			attackDown4 = setup("/player/slash_down3", gp.tileSize * 2, gp.tileSize * 3);
			attackLeft1 = setup("/player/slash_left0", gp.tileSize * 3, gp.tileSize * 3);
			attackLeft2 = setup("/player/slash_left1", gp.tileSize * 3, gp.tileSize * 3);
			attackLeft3 = setup("/player/slash_left2", gp.tileSize * 3, gp.tileSize * 3);
			attackLeft4 = setup("/player/slash_left3", gp.tileSize * 3, gp.tileSize * 3);
			attackRight1 = setup("/player/slash_right0", gp.tileSize * 3, gp.tileSize * 2);
			attackRight2 = setup("/player/slash_right1", gp.tileSize * 3, gp.tileSize * 2);
			attackRight3 = setup("/player/slash_right2", gp.tileSize * 3, gp.tileSize * 2);
			attackRight4 = setup("/player/slash_right3", gp.tileSize * 3, gp.tileSize * 2);
		}
		if (currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/axe_up0", gp.tileSize * 2, gp.tileSize * 3);
			attackUp2 = setup("/player/axe_up1", gp.tileSize * 2, gp.tileSize * 3);
			attackUp3 = setup("/player/axe_up2", gp.tileSize * 2, gp.tileSize * 3);
			attackUp4 = setup("/player/axe_up3", gp.tileSize * 2, gp.tileSize * 3);
			attackDown1 = setup("/player/axe_down0", gp.tileSize * 2, gp.tileSize * 3);
			attackDown2 = setup("/player/axe_down1", gp.tileSize * 2, gp.tileSize * 3);
			attackDown3 = setup("/player/axe_down2", gp.tileSize * 2, gp.tileSize * 3);
			attackDown4 = setup("/player/axe_down3", gp.tileSize * 2, gp.tileSize * 3);
			attackLeft1 = setup("/player/axe_left0", gp.tileSize * 3, gp.tileSize * 3);
			attackLeft2 = setup("/player/axe_left1", gp.tileSize * 3, gp.tileSize * 3);
			attackLeft3 = setup("/player/axe_left2", gp.tileSize * 3, gp.tileSize * 3);
			attackLeft4 = setup("/player/axe_left3", gp.tileSize * 3, gp.tileSize * 3);
			attackRight1 = setup("/player/axe_right0", gp.tileSize * 3, gp.tileSize * 2);
			attackRight2 = setup("/player/axe_right1", gp.tileSize * 3, gp.tileSize * 2);
			attackRight3 = setup("/player/axe_right2", gp.tileSize * 3, gp.tileSize * 2);
			attackRight4 = setup("/player/axe_right3", gp.tileSize * 3, gp.tileSize * 2);
		}
	}
	
	public void update() { 
		if(attacking == true) {
			attack();
		}
		else if (keyH.upPressed == true || keyH.downPressed == true ||
			keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
		
			if (keyH.upPressed == true) {
				direction = "up";
			}
			else if (keyH.downPressed == true) {
				direction = "down";
			}
			else if (keyH.leftPressed == true) {
				direction = "left";
			}
			else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// check NPC collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// check events
			gp.eHandler.checkEvent();
			
			// player can move if collision is false
			if (collisionOn == false && keyH.enterPressed == false) {
				
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			if (keyH.enterPressed == true && attackCanceled == false) {
				if (currentWeapon.type == type_sword) {gp.playSE(7);}
				attacking = true;
				spriteCounter = 0;
			}
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			
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
		} else {
			standCounter++;
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		if (invincible == true) {
			iFramesCounter++;
			if (iFramesCounter > 60) {
				invincible = false;
				iFramesCounter = 0;
			}
		}
	}
	
	public void attack() {
		spriteCounter++;
		if (currentWeapon.type == type_sword) {
			if (spriteCounter <= 5) {
				spriteNum = 1;
			}
			if (spriteCounter > 5 && spriteCounter <= 10) {
				spriteNum = 2;
				detectHit();
			}
			if (spriteCounter > 10 && spriteCounter <= 15) {
				spriteNum = 3;
				detectHit();
			}
			if (spriteCounter > 15 && spriteCounter <= 25) {
				spriteNum = 4;
				detectHit();
			}
			if (spriteCounter > 25) {
				spriteNum = 1;
				spriteCounter = 0;
				attacking = false;
			} 
		}
		if (currentWeapon.type == type_axe) {
			if (spriteCounter <= 15) {
				spriteNum = 1;
			}
			if (spriteCounter > 15 && spriteCounter <= 20) {
				spriteNum = 2;
			}
			if (spriteCounter > 20 && spriteCounter <= 25) {
				spriteNum = 3;
			}
			if(spriteCounter == 20) {gp.playSE(7);}
			if (spriteCounter > 25 && spriteCounter <= 35) {
				spriteNum = 4;
				detectHit();
			}
			if (spriteCounter > 35) {
				spriteNum = 1;
				spriteCounter = 0;
				attacking = false;
			} 
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			String text;
			if(inventory.size() != maxInventorySize) {
				inventory.add(gp.obj[i]);
				text = "Got a "+gp.obj[i].name + "!";
			} else {
				text = "You cannot carry any more!";
			}
			gp.ui.addMessage(text);
			gp.playSE(13);
			gp.obj[i] = null;
		}
	}
	public void detectHit() {
		int currentWorldX = worldX;
		int currentWorldY = worldY;
		int solidAreaWidth = solidArea.width;
		int solidAreaHeight = solidArea.height;
		
		//adjust world x/y for hitbox
		switch(direction) {
		case "up": worldY -= (gp.tileSize - gp.pixelSize*4); break;
		case "down": worldY +=0; worldX -= (gp.tileSize - gp.pixelSize*4); break;
		case "left": worldX -= (gp.tileSize - gp.pixelSize*4); worldY -= (gp.tileSize-gp.pixelSize*2); break;
		case "right": worldX +=0; worldY-=(gp.tileSize - gp.pixelSize*2); break;
		}
		
		solidArea.width = hitbox.width;
		solidArea.height = hitbox.height;
		
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
		damageMonster(monsterIndex);
		
		worldX = currentWorldX;
		worldY = currentWorldY;
		solidArea.width = solidAreaWidth;
		solidArea.height = solidAreaHeight;
	}
	
	public void interactNPC(int i) {
		if (gp.keyH.enterPressed == true) {
			if(i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void contactMonster(int i) {
		if (i != 999) {
			if(invincible == false) {
				gp.playSE(6);
				int damage = gp.monster[i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				life -= damage;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int i) {
		if (i != 999) {
			if(gp.monster[i].invincible == false) {
				gp.playSE(8);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[i].life -= damage;
				
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage(gp.monster[i].name + " slaughtered!");
					gp.ui.addMessage(gp.monster[i].exp + " exp gained!");
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	public void checkLevelUp()  {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			gp.playSE(10);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are level "+level+"! \nPower surges through you!";
		}
	}
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				if (currentWeapon != selectedItem) {
					gp.playSE(14);
				}
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				if (currentShield != selectedItem) {
					gp.playSE(14);
				}
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;} 
			}
			if (attacking == true) {
				tempScreenY = (screenY - gp.tileSize) - gp.pixelSize*3;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
				if (spriteNum == 3) {image = attackUp3;}
				if (spriteNum == 4) {image = attackUp4;} 
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;} 
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
				if (spriteNum == 3) {image = attackDown3;}
				if (spriteNum == 4) {image = attackDown4;} 
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;} 
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				tempScreenY = screenY - gp.tileSize*2;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
				if (spriteNum == 3) {image = attackLeft3;}
				if (spriteNum == 4) {image = attackLeft4;} 
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;} 
			}
			if (attacking == true) {
				tempScreenX = (screenX - gp.tileSize) + gp.pixelSize*8;
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
				if (spriteNum == 3) {image = attackRight3;}
				if (spriteNum == 4) {image = attackRight4;} 
			}
			break;
		}
		// visual invincible state
		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// reset alpha so it's drawn correctly when the i-frames run out
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
