package object;

import Main.GamePanel;
import entity.Projectile;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Fireball";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        down1 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        left1 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        right1 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        up2 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        down2 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        left2 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
        right2 = setup("/projectiles/fireBolt", gp.tileSize, gp.tileSize);
    }


}
