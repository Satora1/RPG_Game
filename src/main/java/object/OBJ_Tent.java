package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Tent extends Entity {
    GamePanel gp;
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp=gp;
        type=type_consumable;
        name="Tent";
        down1=setup("/objects/tent", gp.tileSize, gp.tileSize);
        description="Use to sleep";
        price=3;
        stackable=true;

    }

    @Override
    public boolean use(Entity entity) {
gp.gameState=gp.sleepState;
gp.playSE(10);
gp.player.life=gp.player.maxLife;
gp.player.mana=gp.player.maxMana;
gp.player.getSleepingImage(down1);
//infinite use return false else true
return true;
    }
}
