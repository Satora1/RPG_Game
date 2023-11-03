package monster;

import Main.GamePanel;
import entity.Entity;
import object.OBJ_CoinVal1;
import object.OBJ_Heart;
import object.OBJ_Rock;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Ork extends Entity {
    GamePanel gp;

    public MON_Ork(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Green Ork";
        speed = 3 / 2;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
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
        up1 = setup("/monster/ORK_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/ORK_2", gp.tileSize, gp.tileSize);

        down1 = setup("/monster/ORK_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/ORK_2", gp.tileSize, gp.tileSize);

        left1 = setup("/monster/ORK_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/ORK_2", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/ORK_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/ORK_2", gp.tileSize, gp.tileSize);
    }

    public void update() {
        super.update();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;
        if (onPath == false && tileDistance < 5) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                onPath = true;
            }
//            if (onPath == true && tileDistance > 20) {
//                onPath = false;
//            }
        }

    }

    public void setAction() {
        if (onPath == true) {
            int i = new Random().nextInt(100) + 1;
            if (i > 197 && projectile.alive == false && shotAvilableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);
                gp.projectileList.add(projectile);
                shotAvilableCounter = 0;
            }
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            ;
            searchPath(goalCol, goalRow);
        } else {
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
        }


    }

    public void damageReaction() {
        actionLockCounter = 0;
        // direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop() {
        //CAST DIE
        int i = new Random().nextInt(100) + 1;
        //set monster drop
        if (i < 50) {
            dropItem(new OBJ_CoinVal1(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
