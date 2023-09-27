package entity;

import Main.GamePanel;
import Main.KeyHandler;

import object.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(10, 10, 26, 26);//for collison
        solidAreaDefaultX = 13;
        solidAreaDefaultY = 13;//for door collison

        setDeflautValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDeflautValues() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down";
        //player status
        level = 1;
        strength = 1;
        dextery = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWepon = new OBJ_Sword_Normal(gp);
        currentShieald = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        // projectile = new OBJ_Rock(gp);
        attack = getAttack();
        defense = getDefense();
        maxLife = 6;
        //6 life==3heart
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        // attackArea.width = 36;
        // attackArea.height = 36;
    }

    public void setItems() {
        inventory.add(currentWepon);
        inventory.add(currentShieald);
        inventory.add(new OBJ_Key(gp));

    }

    public int getAttack() {

        attackArea = currentWepon.attackArea;
        return attack = strength * currentWepon.attackValue;
    }

    public int getDefense() {
        return defense = dextery * currentShieald.defenseValue;
    }

    public void getPlayerImage() {

//if(currentWepon.type==type_axe){} for animation

        main = setup("/player/hero", gp.tileSize, gp.tileSize);
        up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        Adown = setup("/player/Attack_down", gp.tileSize, gp.tileSize * 2);
        Aleft = setup("/player/Attack_left", gp.tileSize * 2, gp.tileSize);
        Aright = setup("/player/Attack_right", gp.tileSize * 2, gp.tileSize);
        Aup = setup("/player/Attack_up", gp.tileSize, gp.tileSize * 2);

    }


    public void update() {
        if (attacking == true) {
            attacking();

        } else if (keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {


            if (keyH.upPressed == true) {
                direction = "up";

            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";

            }


//chcek collision tile

            collisionOn = false;
            gp.checker.checkerTile(this);
            gp.checker.checkerTile(this);


            int objectIndex = gp.checker.checkObject(this, true);
            pickUpObject(objectIndex);
//check npc collision
            int npcIndex = gp.checker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
//check monster collision
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
//check interactive tile collision
            int iTileIndex = gp.checker.checkEntity(this, gp.iTile);
            //check event
            gp.eHandler.CheckEvent();

            //if collison is false can move
            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            if (keyH.enterPressed == true && attackCanceled == false) {
                gp.playSE(5);
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spritNum == 1) {
                    spritNum = 2;
                } else if (spritNum == 2) {
                    spritNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvilableCounter == 30
                && projectile.haveResource(this) == true) {
            projectile.set(worldX, worldY, direction, true, this);
//subtract cost (mana,ammo)
            projectile.subtractResources(this);
            //add it to arry list
            gp.projectileList.add(projectile);
            shotAvilableCounter = 0;
            gp.playSE(8);
        }
        //this need to be outside of key if statement!
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvilableCounter < 30) {
            shotAvilableCounter++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spritNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spritNum = 2;
            //save the current worldX,worldY,solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //Adjust players World X/Y for attacking area
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //Check monster colision with update worldX,worldY and solid Area
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.checker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            //after collsion check , resort orginal data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spritNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {
            //PICKUP ONLY ITEMS
            if (gp.obj[i].type == type_pickupOnly) {
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }
            //INVENTORY ITEMS
            else {
                String text;
                if (inventory.size() != maxInventorySize) {
                    inventory.add(gp.obj[i]);
                    gp.playSE(2);
                    text = "Got a " + gp.obj[i].name + "!";
                } else {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }

        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }

        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[i].dying == false) {
                gp.playSE(3);
                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;

            }

        }

    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[i].invincible == false) {
                gp.playSE(3);
                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + "damage");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if (gp.monster[i].life <= 0) {
                    gp.playSE(4);
                    gp.monster[i].dying = true;
                    exp += gp.monster[i].exp;
                    gp.ui.addMessage("killed the " + gp.monster[i].name + " !");
                    gp.ui.addMessage("EXP " + gp.monster[i].exp + " !");
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[i].destructible == true && gp.iTile[i].isCorrectItem(this) == true && gp.iTile[i].invincible==false) {
            gp.iTile[i].playSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;
            if (gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();
            }

        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            maxMana += 1;
            strength++;
            dextery++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(6);
            gp.gameState = gp.dialogueState;
            gp.player.mana = gp.player.maxMana;
            gp.player.life = gp.player.maxLife;
            gp.ui.currentDialogue = "you are level " + level + "now!";
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOfSlot();
        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_spear) {
                currentWepon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();

            }
            if (selectedItem.type == type_shield) {
                currentShieald = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spritNum == 1) {
                        image = up1;
                    }
                    if (spritNum == 2) {
                        image = up2;
                    }
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spritNum == 1) {
                        image = Aup;
                    }
                    if (spritNum == 2) {
                        image = Aup;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spritNum == 1) {
                        image = down1;
                    }
                    if (spritNum == 2) {
                        image = down2;
                    }
                }
                if (attacking == true) {
                    if (spritNum == 1) {
                        image = Adown;
                    }
                    if (spritNum == 2) {
                        image = Adown;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spritNum == 1) {
                        image = left1;
                    }
                    if (spritNum == 2) {
                        image = left2;
                    }
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spritNum == 1) {
                        image = Aleft;
                    }
                    if (spritNum == 2) {
                        image = Aleft;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spritNum == 1) {
                        image = right1;
                    }
                    if (spritNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spritNum == 1) {
                        image = Aright;
                    }
                    if (spritNum == 2) {
                        image = Aright;
                    }
                    break;
                }
        }
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset alfa
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        //DEBUG
        //  g2.setFont(new Font("Arial",Font.PLAIN,26));
        // g2.setColor(Color.white);
        // g2.drawString("Invincible counter"+invincibleCounter,10,400);
    }
}
