package Main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    Graphics2D g2;

    GamePanel gp;
    Font arial_40, arial_70B;

    public boolean messageOn = false;
    public String message = " ";
    int messageCounter = 0;
    public int commandNumber = 0;

    BufferedImage heart_full, heart_half, heart_empty;
    public boolean gameFinished = false;

    public String currentDialogue = " ";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_70B = new Font("Arial", Font.BOLD, 70);
//create HUD object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        //title state
        if (gp.gameState == gp.titleState) {

            drawTitleScreen();
        }
        //pause state
        if (gp.gameState == gp.pauseGame) {
            drawPlayerLife();
            drawPauseScreen();
        }
        //daialogue state
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialougeScreen();
        }
    }

    public void drawPlayerLife() {

       // gp.player.life=6;
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;





        //Draw max life
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        //draw current life
        while (i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life){
                g2.drawImage(heart_full,x,y,null);

            }
            i++;
            x+=gp.tileSize;
        }

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenterdText(text);
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - length / 2;
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXforCenterdText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void drawDialougeScreen() {
        //window
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentDialogue, x, y);
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(c);
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 66F));
        String text = "MIGHTY ADVENTURE";
        int x = getXforCenterdText(text);
        int y = gp.tileSize * 3;
        //main text shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        //main color
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        //hero image
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y = gp.tileSize * 4;
        g2.drawImage(gp.player.main, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
//menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 33F));
        text = "NEW GAME";
        x = getXforCenterdText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {
            //drawImage zamisat draw string można dać
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenterdText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNumber == 1) {
            //drawImage zamisat draw string można dać
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenterdText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNumber == 2) {
            //drawImage zamisat draw string można dać
            g2.drawString(">", x - gp.tileSize, y);

        }
        if (commandNumber == 3) {
            commandNumber = 0;
        }
        if (commandNumber == -1) {
            commandNumber = 2;
        }
    }
}
