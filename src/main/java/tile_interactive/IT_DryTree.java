package tile_interactive;

import Main.GamePanel;
import entity.Entity;

public class IT_DryTree extends InteractiveTile {
    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        this.gp = gp;
        down1 = setup("/tiles_interactive/dryTree", gp.tileSize, gp.tileSize);
        destructible = true;
        life=3;

    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWepon.type == type_spear) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
        return tile;
    }
}
