package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Sword_Normal extends Entity {
    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);
        name="Basic Sword";
        down1=setup("/objects/sword_basic",gp.tileSize,gp.tileSize);
        attackValue=1;
        description="["+name+"]\nAn old sword";


    }

}
