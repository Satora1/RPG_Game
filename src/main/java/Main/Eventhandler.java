package Main;

import java.awt.*;

public class Eventhandler {
    GamePanel gp;
    EventRect eventRect[][];
    boolean canTouchEvent=true;

    int prevEventX,prevEventY;

    public Eventhandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }

    public void CheckEvent() {

        //  check if the player character is more than 1 tile from event
int xDistance=Math.abs(gp.player.worldX-prevEventX);
int yDistance=Math.abs(gp.player.worldY-prevEventY);
int distance=Math.max(xDistance,yDistance);
if(distance>gp.tileSize){
    canTouchEvent=true;
}

if(canTouchEvent==true){

    if (hit(31, 25, "right") == true) {
        damagePit(31,25,gp.dialogueState);}



    if (hit(7, 25, "left") == true) {
        healingPool(7,25,gp.dialogueState);}

}
        //event happens


    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone ==false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
prevEventX=gp.player.worldX;
prevEventY=gp.player.worldY;

            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;

    }

    public void damagePit(int row,int col ,int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "you fall in to a pit";
        gp.player.life -= 1;
       // eventRect[col][row].eventDone=true;
        canTouchEvent=false;

    }

    public void healingPool(int row,int col ,int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "you drink water";
            gp.player.life = gp.player.maxLife;
        }
    }


}
