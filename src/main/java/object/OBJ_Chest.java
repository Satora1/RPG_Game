package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gp){
        super(gp);
        name="Chest";
        down1=setup("/objects/wood_chest",gp.tileSize,gp.tileSize);

    }

}