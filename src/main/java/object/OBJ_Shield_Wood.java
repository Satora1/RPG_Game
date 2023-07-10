package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        name ="Wood Shield";
        down1=setup("/objects/shield",gp.tileSize,gp.tileSize);
        defenseValue=1;
        description="["+name+"]\nAn old shield";
    }
}
