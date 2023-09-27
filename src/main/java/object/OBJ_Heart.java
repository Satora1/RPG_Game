package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {

        super(gp);
        this.gp = gp;
        type= type_pickupOnly;
        name = "Heart";
        value=2;
        down1=setup("/playerHP/HP", gp.tileSize, gp.tileSize);
        image = setup("/playerHP/HP", gp.tileSize, gp.tileSize);
        image2 = setup("/playerHP/half_HP", gp.tileSize, gp.tileSize);
        image3 = setup("/playerHP/empty_HP", gp.tileSize, gp.tileSize);


    }

    public void use(Entity entity) {
        gp.ui.addMessage("Life " + value);
        entity.life += value;

    }
}
