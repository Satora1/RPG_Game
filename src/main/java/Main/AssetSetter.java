package Main;

import entity.NPC_Wizard;
import monster.MON_Ork;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {
gp.obj[0]=new OBJ_Door(gp);
        gp.obj[0].worldX=gp.tileSize*19;
        gp.obj[0].worldY=gp.tileSize*25;
        gp.obj[1]=new OBJ_Door(gp);
        gp.obj[1].worldX=gp.tileSize*30;
        gp.obj[1].worldY=gp.tileSize*25;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Wizard(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
    public void setMonster(){
        gp.monster[0]=new MON_Ork(gp);
        gp.monster[0].worldX=gp.tileSize*25;
        gp.monster[0].worldY=gp.tileSize*33;
    }
}
