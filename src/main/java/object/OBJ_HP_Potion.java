package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_HP_Potion extends Entity {
    GamePanel gp;

    public OBJ_HP_Potion(GamePanel gp) {
        super(gp);
        this.gp=gp;
        value=5;
        type = type_consumable;
        name ="Heal Potion";
        down1=setup("/objects/potionHP",gp.tileSize,gp.tileSize);
        description="["+name+"]\nAn old mighty healing\n potion";
        price=2;
        stackable=true;
    }
    public boolean use(Entity entity){
        gp.gameState=gp.dialogueState;
        gp.ui.currentDialogue="You drink the "+name +" !\n"+
                " You recover hp by "+value;
        entity.life+=value;
        return true;
    }

}
