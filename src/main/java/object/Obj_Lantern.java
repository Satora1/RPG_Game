package object;

import Main.GamePanel;
import entity.Entity;

public class Obj_Lantern extends Entity {
    public Obj_Lantern(GamePanel gp) {
        super(gp);
        type=type_light;
        name="Lantern";
        down1=setup("/objects/lantern",gp.tileSize,gp.tileSize);
        description="[Lantern]\nsee more in dark\n";
        price=2;
        lightRadius=250;
    }
}
