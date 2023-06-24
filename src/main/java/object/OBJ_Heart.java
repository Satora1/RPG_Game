package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {


    public OBJ_Heart(GamePanel gp) {

        super(gp);
        name = "Heart";
        image=setup("/playerHP/HP",gp.tileSize,gp.tileSize);
        image2=setup("/playerHP/half_HP",gp.tileSize,gp.tileSize);
        image3=setup("/playerHP/empty_HP",gp.tileSize,gp.tileSize);



    }
}
