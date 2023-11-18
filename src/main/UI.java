package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import object.*;
import entity.Entity;

public class UI {

	
	GamePanel gp;
	Graphics2D g2;
	Font gb_font; 
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished;
	public String currentDialogue = "";
	public int commandNum = 0;
	BufferedImage titleScreen, pointer, heart_full, heart_half, heart_blank;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		InputStream is = getClass().getResourceAsStream("/font/gb.ttf");
		try {
			pointer = ImageIO.read(getClass().getResourceAsStream("/objects/pointer" + ".png"));
			titleScreen = ImageIO.read(getClass().getResourceAsStream("/tiles/title" + ".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		try {
			gb_font = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		// create HUD object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(gb_font);
		g2.setColor(Color.black);
		// switch will be better when there's 5+ states
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// GAME STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
		}
	}
	
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		while (i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}
	// TODO: Align Title Screen to original game
	public void drawTitleScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		String text = "";
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		g2.drawImage(titleScreen, x, y, gp.tileSize*12, gp.tileSize*6, null);
		
		// player
		x = gp.screenWidth/2 - (gp.tileSize*6)/2;
		y += gp.tileSize*4;		
		// menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
		
		text = "NEW GAME";
		x = gp.tileSize*2;
		y += gp.tileSize*5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawImage(pointer, x-(gp.tileSize*2)+(gp.tileSize/2), y-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}
		
		text = "CONTINUE";
		x = gp.tileSize*10;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawImage(pointer, x-(gp.tileSize*2)+(gp.tileSize/2), y-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawImage(pointer, x-(gp.tileSize*2)+(gp.tileSize/2), y-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += gp.tileSize;
		}
		
	}
	public void drawCharacterScreen() {
		// create frame
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(16f));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 36;
		
		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Str", textX, textY);
		textY += lineHeight;
		g2.drawString("Dex", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Lv.", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		// VALUES
		int tailX = (frameX + frameWidth) -30;
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforRightAlign(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight/2;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
		textY += gp.tileSize+16;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0); // fourth parameter is alpha
		g2.setBackground(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);	
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int getXforRightAlign(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}

