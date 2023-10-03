package Main;

import entity.NPC_Merchant;
import entity.NPC_Wizard;
import monster.MON_Boss;
import monster.MON_Ork;

import object.*;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_House;


public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 19;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 30;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[mapNum][i] = new OBJ_CoinVal1(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 28;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[mapNum][i] = new OBJ_CoinVal1(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 28;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        gp.obj[mapNum][i] = new OBJ_Spear(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 22;
        i++;
        gp.obj[mapNum][i] = new OBJ_Iron_Shield(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 22;
        i++;
        gp.obj[mapNum][i] = new OBJ_HP_Potion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 26;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;

        //for difrent ma do
        //mapNum=1... and same as up
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_Wizard(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        mapNum=1;
        i=0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 10;
        gp.npc[mapNum][i].worldY = gp.tileSize * 6;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 32;
        i++;
        gp.monster[mapNum][i] = new MON_Boss(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 32;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 25);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 25);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 25);
        i++;
        gp.iTile[mapNum][i] = new IT_House(gp, 37, 22);
    }
}
