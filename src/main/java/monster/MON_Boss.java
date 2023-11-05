package monster;

import Main.GamePanel;
import entity.Entity;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Boss extends Entity {
    GamePanel gp;
    public MON_Boss(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Drone";
        speed =4;
        maxLife = 10;
        life = maxLife;
        attack = 6;
        defense = 3;
        exp = 15;
        projectile = new OBJ_Rock(gp);
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();

    }

    public void getImage() {
        up1 = setup("/monster/drone", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/drone", gp.tileSize, gp.tileSize);

        down1 = setup("/monster/drone", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/drone", gp.tileSize, gp.tileSize);

        left1 = setup("/monster/drone", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/drone", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/drone", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/drone", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;//pick up a numer from 1-100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;

        }
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && projectile.alive == false && shotAvilableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            for (int j=0;j<gp.projectile[j].length;j++){
                if(gp.projectile[gp.currnetMap][j]==null){
                    gp.projectile[gp.currnetMap][j]=projectile;
                    break;
                }
            }
            shotAvilableCounter = 0;
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
