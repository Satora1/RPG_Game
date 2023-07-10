package object;

import Main.GamePanel;
import entity.Entity;


public class OBJ_Iron_Shield extends Entity {

    public OBJ_Iron_Shield(GamePanel gp) {
        super(gp);
        type = type_shield;
        name ="Iron Shield";
        down1=setup("/objects/Iron_shield",gp.tileSize,gp.tileSize);
        defenseValue=2;
        description="["+name+"]\nAn old rusty iron shield";
    }
}
