package entity;

import Main.GamePanel;

import java.util.Random;


public class NPC_Wizard extends Entity {

    public NPC_Wizard(GamePanel gp) {
        super(gp);
        direction = "down";
        speed =  2;
        getImage();
        setDialogue();
    }

    public void getImage() {


        up1 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/wizzard",gp.tileSize,gp.tileSize);
    }

    public void setAction() {
        if (onPath == true) {
//int goalCol=34;
//int goalRow=25;
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

    public void setDialogue() {
        dialogues[3] = "WHAZAp";
        dialogues[0] = "Cześć!";
        dialogues[1] = "Jak się masz?";
        dialogues[2] = "Co u ciebie słychać?";
        dialogues[4] = "Co u ciebioooo ooooooooooo ooooooooooo \noooooo ooooooo oooo ooooo ooooo " +
                " \noooo o o oooooe słychać?";
    }


    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        super.speak();
        onPath=true;

    }

}
