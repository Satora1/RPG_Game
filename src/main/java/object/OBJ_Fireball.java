package object;

import Main.GamePanel;
import entity.Entity;
import entity.Projectile;

import java.awt.*;

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
        knockBackPower=0;
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

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
public void subtractResources(Entity user){
        user.mana-=useCost;

}
    public Color getParticleColor(){
        Color color = new Color(255,50,0);
        return  color;
    }
    public int getParticleSize(){
        int size =10    ;
        return size;
    }
    public  int  getParticleSpeed(){
        int speed =1;
        return speed;
    }
    public  int getParticleMaxLife(){
        int maxLife=20;
        return maxLife;
    }
}
