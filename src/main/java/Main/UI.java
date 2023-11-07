package Main;

import entity.Entity;
import object.OBJ_CoinVal1;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class UI {

    Graphics2D g2;

    GamePanel gp;
    Font arial_40, arial_70B;

    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int commandNumber = 0;

    BufferedImage heart_full, heart_half, heart_empty, mana, mana_empty, coin;
    public boolean gameFinished = false;
    public int sloPlayertCol = 0;
    public int slotPlayerRow = 0;
    public int slotNPCCol = 0;
    public int slotNPCRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
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
        Entity crystal = new OBJ_ManaCrystal(gp);
        mana = crystal.image;
        mana_empty = crystal.image2;
        Entity coinVal1 = new OBJ_CoinVal1(gp);
        coin = coinVal1.down1;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) throws IOException {
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
            drawInventory(gp.player, true);
        }
        //option state
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
        //game over state
        if (gp.gameState == gp.gameOverState) {
            gameOverScreen();
        }
        //transition state
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }
        //trade State
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;

        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select() {
        drawDialougeScreen();
        //Draw window for selection
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);
        //Draw text
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed == true) {
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if (commandNumber == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed == true) {
                commandNumber = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "See you";

            }
        }

    }

    public void trade_sell() {
        //draw player inventory
        drawInventory(gp.player, true);
        int x;
        int y;
        int width;
        int height;
        //draw hint window
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);
        //draw player coin window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins : " + gp.player.coin, x + 24, y + 60);
        //draw price window
        int itemIndex = getItemIndexOfSlot(sloPlayertCol, slotPlayerRow);
        if (itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = " " + price;
            x = getXforAlignToRightText(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);
            //sell an item
            if (gp.keyH.enterPressed == true) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWepon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShieald) {
                    commandNumber = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = " You can not sell this item!";
                    drawDialougeScreen();
                } else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    } else {
                        gp.player.inventory.remove(itemIndex);

                    }
                    gp.player.coin += price;
                }
            }
        }

    }

    public void trade_buy() {
        //draw palyer inventory
        drawInventory(gp.player, false);
        //draw npc inventory
        drawInventory(npc, true);
        //draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);
        //draw player coin window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins : " + gp.player.coin, x + 24, y + 60);
        //draw price window
        int itemIndex = getItemIndexOfSlot(slotNPCCol, slotNPCRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = npc.inventory.get(itemIndex).price;
            String text = " " + price;
            x = getXforAlignToRightText(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);
            //buy a item
            if (gp.keyH.enterPressed == true) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "need more coins";
                    drawDialougeScreen();
                } else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    } else {
                        subState = 0;
                        gp.gameState = gp.dialogueState;
                        currentDialogue = "no space in inventory";
                    }
                }
            }
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        if (counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currnetMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.prevEventX = gp.player.worldX;
            gp.eHandler.prevEventY = gp.player.worldY;
        }
    }

    public void drawOptionsScreen() throws IOException {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        //sub window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                end_gameConfiramtion(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) throws IOException {
        int textX;
        int textY;
//title
        String text = "OPTIONS";
        textX = getXforCenterdText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //full screen
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full screen", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                if (gp.fullScreenOn == false) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn == true) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }

        }
        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
        }
        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNumber == 2) {
            g2.drawString(">", textX - 25, textY);
        }
        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNumber == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNumber = 0;
            }
        }
        //EndGame
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNumber == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 3;
                commandNumber = 0;
            }
        }
        //Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNumber = 0;
            }
        }
        //full screen check box
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn == true) {
            g2.fillRect(textX, textY, 24, 24);
        }
        //music volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        //SE volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.soundEffect.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        gp.config.saveConfig();
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
        //DRAW MAX MANAX
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(mana_empty, x, y, null);
            i++;
            x += 35;
        }
//DRAW MANA
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while (i < gp.player.mana) {
            g2.drawImage(mana, x, y, null);
            i++;
            x += 35;
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
        int x = gp.tileSize / 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
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
        final int frameX = gp.tileSize * 2;
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
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
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

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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

        value = String.valueOf(gp.player.attack);
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

        g2.drawImage(gp.player.currentWepon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShieald.down1, tailX - gp.tileSize, textY - 24, null);
    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;
        if (entity == gp.player) {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = sloPlayertCol;
            slotRow = slotPlayerRow;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = slotNPCCol;
            slotRow = slotNPCRow;
        }
        //frame

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        //draw player items
        for (int i = 0; i < entity.inventory.size(); i++) {

            //Equip cursor
            if (entity.inventory.get(i) == entity.currentWepon ||
                    entity.inventory.get(i) == entity.currentShieald) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            //Dispaly amount
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;
                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;
                //shadow for a number
                g2.setColor(Color.gray);
                g2.drawString(s, amountX, amountY);
                //Number
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);
            }
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
        if (cursor == true) {
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
            //description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameWidth;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;
            //draw description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));
            int itemIndex = getItemIndexOfSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }

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

    public int getItemIndexOfSlot(int slotCol, int slotRow) {
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

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDialogue = "This change affect \nif you restart the game\n";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
//BACK
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNumber = 3;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;
        //Title
        String text = "Control";
        textX = getXforCenterdText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        textX = frameX + gp.tileSize / 2;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX + gp.tileSize, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX + gp.tileSize, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX + gp.tileSize, textY);
        textY += gp.tileSize;
        g2.drawString("ESCAPE", textX, textY);
        //Back
        textX = frameX += gp.tileSize;
        textY = frameY += gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
            }
        }
    }

    public void end_gameConfiramtion(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDialogue = "Quit the game and \nreturn to title screen?\n";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        String text = "YES";
        textX = getXforCenterdText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
            }
        }
        text = "NO";
        textX = getXforCenterdText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNumber = 4;
            }
        }

    }

    public void gameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int X;
        int Y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "GAME OVER";
        g2.setColor(Color.black);
        X = getXforCenterdText(text);
        Y = gp.tileSize * 4;
        g2.drawString(text, X, Y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, X - 4, Y - 4);
        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "RETRY";
        X = getXforCenterdText(text);
        Y += gp.tileSize * 4;
        g2.drawString(text, X, Y);
        if (commandNumber == 0) {
            g2.drawString(">", X - 40, Y);
        }
        //back to title screen
        text = "QUIT";
        X = getXforCenterdText(text);
        Y += 55;
        g2.drawString(text, X, Y);
        if (commandNumber == 1) {
            g2.drawString(">", X - 40, Y);
        }

    }
}
