package tile_interactive;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class IT_House extends  InteractiveTile{
    public IT_House(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        this.gp = gp;
        down1 = setup("/tiles/House", gp.tileSize, gp.tileSize);
        destructible = false;
        solidArea.x=0;
        solidArea.y=0;
        solidArea.width=0;
        solidArea.height=0;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;

    }


    public void playSE() {
    }

}