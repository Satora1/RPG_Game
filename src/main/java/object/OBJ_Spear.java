package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Spear extends Entity {
    public OBJ_Spear(GamePanel gp){
        super(gp);
        type = type_spear;
        name="Spear";
        down1=setup("/objects/spear",gp.tileSize,gp.tileSize);
        attackValue=2;
        attackArea.width=40;
        attackArea.height=30;
        description="["+name+"]\nAn old wooden spear";


    }
}
