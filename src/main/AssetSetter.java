package main;

import entity.*;
import interactive_tile.IT_DryTree;
import monster.*;
import object.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = gp.tileSize*25;
		gp.obj[i].worldY = gp.tileSize*23;
		i++;
		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		gp.obj[i] = new OBJ_ManaCrystal(gp);
		gp.obj[i].worldX = gp.tileSize*26;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize*33;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Shield_L2(gp);
		gp.obj[i].worldX = gp.tileSize*33;
		gp.obj[i].worldY = gp.tileSize*7;
		i++;
		gp.obj[i] = new OBJ_Potion(gp);
		gp.obj[i].worldX = gp.tileSize*37;
		gp.obj[i].worldY = gp.tileSize*7;
		i++;
	}
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	public void setMonsters() {
		int i = 0;
		gp.monster[i] = new MON_Flame(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*36;
		i++;
		gp.monster[i] = new MON_Flame(gp);
		gp.monster[i].worldX = gp.tileSize*24;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		gp.monster[i] = new MON_Flame(gp);
		gp.monster[i].worldX = gp.tileSize*25;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		gp.monster[i] = new MON_Flame(gp);
		gp.monster[i].worldX = gp.tileSize*26;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		gp.monster[i] = new MON_Flame(gp);
		gp.monster[i].worldX = gp.tileSize*27;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
	}
	
	public void setInteractiveTile() {
		int i = 0;
		gp.iTile[i] = new IT_DryTree(gp, 27, 7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 28, 7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 29, 7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 30, 7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 31, 7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 32, 7);
		i++;
	}
}
