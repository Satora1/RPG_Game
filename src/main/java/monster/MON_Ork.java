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
        defaultSpeed = 1;
        speed = defaultSpeed;
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


    public void setAction() {
        if (onPath == true) {
            //check if stop chasing
            checkStopChasing(gp.player, 15, 100);
            //searh for the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            //check if shoot projectile
            shootOrNot(200, 30);
        } else {
            //start chasing
            checkStaartChasing(gp.player, 5, 100);
            //random movment
            getRandomDirection();

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
