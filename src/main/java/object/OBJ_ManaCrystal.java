package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = "Mana Crystal";
        value = 1;
        down1 = setup("/playerMP/mana", gp.tileSize, gp.tileSize);
        image = setup("/playerMP/mana", gp.tileSize, gp.tileSize);
        image2 = setup("/playerMP/empty_mana", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.ui.addMessage("Mana " + value);
        entity.mana += value;
        return true;
    }
}
