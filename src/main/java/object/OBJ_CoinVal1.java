package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_CoinVal1 extends Entity {
    GamePanel gp;

    public OBJ_CoinVal1(GamePanel gp) {

        super(gp);
        this.gp = gp;
        type =type_pickupOnly;
        name = "Bronze Coin";
        value = 1;
        down1 = setup("/objects/coinVAL1", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
    gp.playSE(2);
        gp.ui.addMessage("Coin "+ value);
        gp.player.coin+=value;
        return true;

    }
}
