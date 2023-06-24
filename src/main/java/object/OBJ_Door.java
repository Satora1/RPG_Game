package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp){
        super(gp);
        name="Door";
down1=setup("/objects/double_dors",gp.tileSize,gp.tileSize)  ;
        collision=false;
    }
}
