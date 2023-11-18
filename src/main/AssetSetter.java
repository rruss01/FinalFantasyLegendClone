package main;

import entity.*;
import monster.*;
import object.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
	}
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	public void setMonsters() {
		gp.monster[0] = new MON_Flame(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = gp.tileSize*36;
		
		gp.monster[1] = new MON_Flame(gp);
		gp.monster[1].worldX = gp.tileSize*24;
		gp.monster[1].worldY = gp.tileSize*37;
	}
}
