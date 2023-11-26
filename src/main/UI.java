package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import object.*;
import entity.Entity;

public class UI {

	
	GamePanel gp;
	Graphics2D g2;
	Font gb_font; 
	public boolean messageOn = false;

	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished;
	public String currentDialogue = "";
	public int commandNum = 0;
	BufferedImage titleScreen, pointer, heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
	public int slotCol = 0;
	public int slotRow = 0;
	int subState = 0;
	
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
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		
	}
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
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
			drawMessage();
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
			drawInventory();
		}
		// OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		// OPTIONS STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
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
		
		// draw max mana
		x = gp.tileSize/2;
		y = gp.tileSize/2 + gp.tileSize;
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += gp.tileSize/2;
		}
		
		// draw current mana
		x = gp.tileSize/2;
		y = gp.tileSize/2 + gp.tileSize;
		i = 0;
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += gp.tileSize/2;
		}
	}
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
		
		for (int i = 0; i < message.size(); i++) {
			if (message.get(i) != null) {
				g2.setColor(new Color(0,180,0));
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.green);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; // same as messageCounter++; but works w/ ArrayList
				messageCounter.set(i, counter);
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	// TODO: Align Title Screen to original game
	public void drawTitleScreen() {
		g2.setColor(Color.white);
		g2.fillRect(0, 0, gp.maxScreenCol*gp.tileSize, gp.maxScreenRow*gp.tileSize);
		
		g2.setColor(Color.black);
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
		x = (int)(gp.tileSize*13.5);
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
		g2.drawString("Mana", textX, textY);
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
		textY += lineHeight + 8;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 4;
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
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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
		textY += lineHeight/3;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
		textY += lineHeight + 4;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(20f));
		
		// sub window
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// title
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// full screen on/off
		textX = frameX + gp.tileSize + (gp.tileSize/2);
		textY += gp.tileSize*2;
		g2.drawString("Full screen", textX, textY);
		if (commandNum == 0) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				} else if (gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		// music
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}
		
		// se
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if (commandNum == 2) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}

		// controls
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 3) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
				
		// end game
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 4) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		// back
		textY += gp.tileSize*2;
		g2.drawString("Back", textX, textY);
		if (commandNum == 5) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
			}
		}
		
		// fullscreen check box
		textX = frameX + (int)(gp.tileSize * 6.5);
		textY = frameY + gp.tileSize*2 + (gp.tileSize/2);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		// music volume
		textY += gp.tileSize;
		g2.drawRect(textX-120+(gp.tileSize/2), textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX-120+(gp.tileSize/2), textY, volumeWidth, 24);
		
		// se volume
		textY += gp.tileSize;
		g2.drawRect(textX-120+(gp.tileSize/2), textY, 120, 24);
		volumeWidth = 24*gp.se.volumeScale;
		g2.fillRect(textX-120+(gp.tileSize/2), textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	
	public void options_endGameConfirmation(int frX, int frY) {
		int textX = frX + gp.tileSize;
		int textY = frY + gp.tileSize*3;
		
		currentDialogue = "Quit game and\nreturn to title\nscreen?";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += gp.tileSize;
		}
		
		// YES
		String text = "yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
			}
		}
		
		// NO
		text = "no";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
		
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75f));
		
		text = "game over";
		// text shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		// main text
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		g2.setFont(g2.getFont().deriveFont(36f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawImage(pointer, x-(gp.tileSize)-(gp.tileSize/4), y-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}
		
		text = "quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawImage(pointer, x-(gp.tileSize)-(gp.tileSize/4), y-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
		}
	}
	
	public void drawInventory() {
		// frame
		int frameX = gp.tileSize*12;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// slots
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		
		// draw player inventory
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.drawString("E", slotX+(gp.tileSize-15), slotY+(gp.tileSize-5));
			}
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		// cursor
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		// draw cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// description window
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize*4;
		drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
		// draw description text
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(14f));
		
		int itemIndex = getItemIndexOnSlot();
		
		if(itemIndex < gp.player.inventory.size()) {
			
			// drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); // draws only when there's an item
			
			for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
		}
	}
	
	public void options_fullScreenNotification(int fX, int fY) {
		int textX = fX + gp.tileSize;
		int textY = fY + gp.tileSize*3;
		
		currentDialogue = "The change will\ntake effect after\nrestarting the\ngame.";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += gp.tileSize;
		}
		textX = fX + gp.tileSize + (gp.tileSize/2);
		textY = fY + gp.tileSize*9;
		g2.drawString("back", textX, textY);
		if (commandNum == 0) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}
	
	public void options_control(int frX, int frY) {
		int textX;
		int textY;
		
		String text = "Controls";
		textX = getXforCenteredText(text);
		textY = frY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("move", textX, textY); textY += gp.tileSize;
		g2.drawString("attack", textX, textY); textY += gp.tileSize;
		g2.drawString("cast", textX, textY); textY += gp.tileSize;
		g2.drawString("view stats", textX, textY); textY += gp.tileSize;
		g2.drawString("pause", textX, textY); textY += gp.tileSize;
		g2.drawString("options", textX, textY); textY += gp.tileSize;
		
		textX = frX + (int)(gp.tileSize * 5.5);
		textY = frY + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;
		
		// Back
		textX = frX + gp.tileSize + (gp.tileSize/2);
		textY = frY + gp.tileSize*9;
		g2.drawString("back", textX, textY);
		if (commandNum == 0) {
			g2.drawImage(pointer, textX-(gp.tileSize)-(gp.tileSize/4), textY-(gp.tileSize/2), gp.tileSize, gp.tileSize, null);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}

		
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow *5);
		return itemIndex;
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0); // fourth parameter is alpha
		g2.setColor(c); // need to set color or subsequent windows will be white
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

