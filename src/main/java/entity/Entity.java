package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import javax.lang.model.UnknownEntityException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    GamePanel gp;

    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, main;
    public BufferedImage Aup, Adown, Aleft, Aright;

    public int spriteCounter = 0;


    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spritNum = 1;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public int shotAvilableCounter = 0;
    public int dyingCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;


    public int solidAreaDefaultX, solidAreaDefaultY;

    public int actionLockCounter = 0;


    public int invincibleCounter = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    //character status
    public int level;
    public int strength;
    public int dextery;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWepon;
    public Entity currentShieald;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public Projectile projectile;

    //Item atribiute

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int attackValue;
    public int defenseValue;
    public String description = " ";
    public int useCost;
    String dialogues[] = new String[20];
    public int value;
    //TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_spear = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void checkDrop() {

    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currnetMap][i] == null) {
                gp.obj[gp.currnetMap][i] = droppedItem;
                gp.obj[gp.currnetMap][i].worldX = worldX;//dead moster
                gp.obj[gp.currnetMap][i].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0;  //6pix
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Entity genartor, Entity target) {
        Color color = genartor.getParticleColor();
        int size = genartor.getParticleSize();
        int speed = genartor.getParticleSpeed();
        int maxLife = genartor.getParticleMaxLife();
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.projectileList.add(p1);
        gp.projectileList.add(p2);
        gp.projectileList.add(p3);
        gp.projectileList.add(p4);
    }

    public void damageReaction() {

    }

    public void speak() {
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }

    public void use(Entity entity) {
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.checker.checkerTile(this);
        gp.checker.checkObject(this, false);
        gp.checker.checkEntity(this, gp.npc);
        gp.checker.checkEntity(this, gp.monster);
        gp.checker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.checker.checkPlayer(this);
        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
        //if colison false can move
        if (collisionOn == false) {
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
        spriteCounter++;
        if (spriteCounter > 15) {
            if (spritNum == 1) {
                spritNum = 2;
            } else if (spritNum == 2) {
                spritNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvilableCounter < 30) {
            shotAvilableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            //we can give damage
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (screenX >= -gp.tileSize && screenX <= gp.getPanelWidth() &&
                screenY >= -gp.tileSize && screenY <= gp.getPanelHeight()) {
            switch (direction) {
                case "up":
                    if (spritNum == 1) {
                        image = up1;
                    }
                    if (spritNum == 2) {
                        image = up2;
                    }

                    break;
                case "down":
                    if (spritNum == 1) {
                        image = down1;
                    }
                    if (spritNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spritNum == 1) {
                        image = left1;
                    }
                    if (spritNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spritNum == 1) {
                        image = right1;
                    }
                    if (spritNum == 2) {
                        image = right2;
                    }
                    break;
            }

            //MONSTER HP
            if (type == 2 && hpBarOn == true) {
                double onScale = (double) gp.tileSize / maxLife;
                double hpBarValue = onScale * life;

                g2.setColor(Color.gray);
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(Color.red);
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
                hpBarCounter++;
                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                ChangeAlfa(g2, 0.4F);
            }
            if (dying == true) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            ChangeAlfa(g2, 1F);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= i) {
            ChangeAlfa(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            ChangeAlfa(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            ChangeAlfa(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            ChangeAlfa(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            ChangeAlfa(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            ChangeAlfa(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            ChangeAlfa(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            ChangeAlfa(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void ChangeAlfa(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int hight) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, hight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
