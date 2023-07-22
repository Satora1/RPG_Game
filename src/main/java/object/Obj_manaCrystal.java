package object;

import Main.GamePanel;
import entity.Entity;

public class Obj_manaCrystal extends Entity {
    GamePanel gp;
    public Obj_manaCrystal(GamePanel gp){
        super(gp);
        this.gp=gp;
        name="Mana Crystal";
        image=setup("/playerMP/mana",gp.tileSize,gp.tileSize);
        image2=setup("/playerMP/empty_mana",gp.tileSize,gp.tileSize);
    }
}
