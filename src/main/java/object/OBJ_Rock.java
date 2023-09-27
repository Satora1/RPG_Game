package object;

import Main.GamePanel;
import entity.Entity;
import entity.Projectile;

import java.awt.*;

public class OBJ_Rock extends Projectile {
    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/rock", gp.tileSize, gp.tileSize);

    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResources(Entity user){
        user.ammo-=useCost;

    }
    public Color getParticleColor(){
        Color color = new Color(55,50,0);
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
