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
        int i = 0 ;
        gp.obj[mapNum][i] = new OBJ_CoinVal1(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 43;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 27;
        gp.obj[mapNum][i].worldY = gp.tileSize * 53;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 55;
        gp.obj[mapNum][i].worldY = gp.tileSize * 53;
        i++;
        gp.obj[mapNum][i] = new OBJ_CoinVal1(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 46;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_Spear(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 47;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_Iron_Shield(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 48;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_HP_Potion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 49;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_HP_Potion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 50;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_HP_Potion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 51;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 52;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 53;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp,new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 54;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new Obj_Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 55;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.obj[mapNum][i] = new OBJ_Tent(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 56;
        gp.obj[mapNum][i].worldY = gp.tileSize * 42;

        //for difrent ma do
        //mapNum=1... and same as up
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_Wizard(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 49;
        gp.npc[mapNum][i].worldY = gp.tileSize * 46;
        i++;
        mapNum=1;
        i=0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 25;
        gp.npc[mapNum][i].worldY = gp.tileSize * 23;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 64;
        i++;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 65;
        i++;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 66;
        i++;
        gp.monster[mapNum][i] = new MON_Ork(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 67;
        i++;
//        gp.monster[mapNum][i] = new MON_Boss(gp);
//        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
//        gp.monster[mapNum][i].worldY = gp.tileSize * 32;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 53);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 34, 53);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 35, 53);
        i++;
        gp.iTile[mapNum][i] = new IT_House(gp, 58, 53);
    }
}
