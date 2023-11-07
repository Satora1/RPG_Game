package object;

import Main.GamePanel;
import entity.Entity;



public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp=gp;
        type= type_consumable;
        name="Key";
        down1=setup("/objects/key",gp.tileSize,gp.tileSize);
        description="["+name+"]\nAn old Key";
        price=3;
        stackable=true;
    }
    public boolean use(Entity entity){
        gp.gameState=gp.dialogueState;
        int objIndex=getDetected(entity,gp.obj,"Door");
        if(objIndex !=999){
            gp.ui.currentDialogue="You open the door with "+name;
            gp.playSE(10);
            gp.obj[gp.currnetMap][objIndex]=null;
            return true;
        }
        else {gp.ui.currentDialogue="Cant do that";
        return false;}

    }
}
