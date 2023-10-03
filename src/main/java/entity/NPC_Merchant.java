package entity;

import Main.GamePanel;
import object.OBJ_HP_Potion;
import object.OBJ_Key;

public class NPC_Merchant extends Entity{
    public NPC_Merchant(GamePanel gp) {
        super(gp);
        direction = "down";
        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {


        up1 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/mrsPurple",gp.tileSize,gp.tileSize);

    }
    public void setDialogue() {
        dialogues[0] = "want some good stuff?";

    }

    public void setItems(){
        inventory.add(new OBJ_HP_Potion(gp));
        inventory.add(new OBJ_Key(gp));

    }
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        super.speak();
        gp.gameState=gp.tradeState;
        gp.ui.npc=this;

    }
}
