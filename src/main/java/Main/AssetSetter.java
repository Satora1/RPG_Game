package Main;

import entity.NPC_Wizard;
import monster.MON_Ork;

import object.*;


public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {
        int i =0;
        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = gp.tileSize * 19;
        gp.obj[i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = gp.tileSize * 30;
        gp.obj[i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 28;
        gp.obj[i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 28;
        gp.obj[i].worldY = gp.tileSize * 23;
        i++;
        gp.obj[i] = new OBJ_Spear(gp);
        gp.obj[i].worldX = gp.tileSize * 26;
        gp.obj[i].worldY = gp.tileSize * 22;
        i++;
        gp.obj[i] = new OBJ_Iron_Shield(gp);
        gp.obj[i].worldX = gp.tileSize * 24;
        gp.obj[i].worldY = gp.tileSize * 22;
        i++;
        gp.obj[i] = new OBJ_HP_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 24;
        gp.obj[i].worldY = gp.tileSize * 21;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_Wizard(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMonster() {
        int i = 0;
        gp.monster[i] = new MON_Ork(gp);
        gp.monster[i].worldX = gp.tileSize * 25;
        gp.monster[i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[i] = new MON_Ork(gp);
        gp.monster[i].worldX = gp.tileSize * 26;
        gp.monster[i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[i] = new MON_Ork(gp);
        gp.monster[i].worldX = gp.tileSize * 24;
        gp.monster[i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[i] = new MON_Ork(gp);
        gp.monster[i].worldX = gp.tileSize * 25;
        gp.monster[i].worldY = gp.tileSize * 32;
    }
}
