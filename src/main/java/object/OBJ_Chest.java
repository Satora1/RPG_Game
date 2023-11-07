package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        type = type_obstacle;
        name = "Chest";
        image = setup("/objects/wood_chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/opne_chest", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void interact() {
        gp.gameState = gp.dialogueState;
        if (opened == false) {
            gp.playSE(10);
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find loot " + loot.name + " !");
            if (gp.player.canObtainItem(loot)==false) {
                sb.append("Your inventory is full");
            } else {
                sb.append("You get " + loot.name);
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        } else {
            gp.ui.currentDialogue = "Its empty";
        }
    }

}
