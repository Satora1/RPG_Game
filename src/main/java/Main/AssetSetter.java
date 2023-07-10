package Main;

import entity.NPC_Wizard;
import monster.MON_Ork;

import object.OBJ_Door;


public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldX = gp.tileSize * 19;
        gp.obj[0].worldY = gp.tileSize * 25;
        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = gp.tileSize * 30;
        gp.obj[1].worldY = gp.tileSize * 25;

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
