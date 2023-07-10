package Main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {

    Graphics2D g2;

    GamePanel gp;
    Font arial_40, arial_70B;

    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int commandNumber = 0;

    BufferedImage heart_full, heart_half, heart_empty;
    public boolean gameFinished = false;
    public int slotCol = 0;
    public int slotRow = 0;
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

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
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
        //character state
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
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
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);

            }
            i++;
            x += gp.tileSize;
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

    public void drawCharacterScreen() {
        //CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        //Names paramiters
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        // values
        int tailX = (frameX + frameWidth) - 30;
        //rest textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dextery);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWepon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShieald.down1, tailX - gp.tileSize, textY - 14, null);
    }

    public void drawInventory() {
        //frame
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        //draw player items
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        //cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        //draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        //description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameWidth;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
        //draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        int itemIndex = getItemIndexOfSlot();
        if (itemIndex < gp.player.inventory.size()) {

            for(String line:gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX,textY);
                textY+=32;
            }

        }
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
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

    public int getItemIndexOfSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;//that's message counter++
                messageCounter.set(i, counter);//set counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
}
