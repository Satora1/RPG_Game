package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

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
    }

    public void setDeflautValues() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down";
        //player status
        maxLife = 6;
        //6 life==3heart
        life = maxLife;
        attackArea.width=36;
        attackArea.height=36;
    }

    public void getPlayerImage() {

        main = setup("/player/hero",gp.tileSize,gp.tileSize);
        up1 = setup("/player/up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/player/up_2",gp.tileSize,gp.tileSize);
        left1 = setup("/player/left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/player/left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/player/right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/player/right_2",gp.tileSize,gp.tileSize);
        down1 = setup("/player/down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/player/down_2",gp.tileSize,gp.tileSize);
    }
    public  void getPlayerAttackImage(){
        Adown=setup("/player/Attack_down",gp.tileSize,gp.tileSize*2);
        Aleft=setup("/player/Attack_left",gp.tileSize*2,gp.tileSize);
        Aright=setup("/player/Attack_right",gp.tileSize*2,gp.tileSize);
        Aup=setup("/player/Attack_up",gp.tileSize,gp.tileSize*2);

    }


    public void update() {
if(attacking==true){
    attacking();

}
    else    if (keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed==true) {


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


            int objectIndex = gp.checker.checkObject(this, true);
            pickUpObject(objectIndex);
//check npc collision
            int npcIndex = gp.checker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
//check monster collision
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //check event
            gp.eHandler.CheckEvent();

            //if collison is false can move
            if (collisionOn == false&& keyH.enterPressed==false) {
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
        //this need to be outside of key if statement!
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void attacking(){
        spriteCounter++;
        if(spriteCounter<=5){
            spritNum=1;
        }
        if(spriteCounter>5&&spriteCounter<=25){
            spritNum=2;
            //save the current worldX,worldY,solidArea
            int currentWorldX=worldX;
            int currentWorldY=worldY;
            int solidAreaWidth=solidArea.width;
            int solidAreaHeight=solidArea.height;
            //Adjust players World X/Y for attacking area
            switch (direction){
                case"up":worldY-=attackArea.height;break;
                case "down":worldY+=attackArea.height;break;
                case"left":worldX-=attackArea.width;break;
                case "right":worldX+=attackArea.width;break;
            }
            //attackArea becomes solidArea
            solidArea.width=attackArea.width;
            solidArea.height=attackArea.height;
            //Check monster colision with update worldX,worldY and solid Area
            int monsterIndex=gp.checker.checkEntity(this, gp.monster);
          damageMonster(monsterIndex);
           //after collsion check , resort orginal data
            worldX=currentWorldX;
            worldY=currentWorldY;
            solidArea.width=solidAreaWidth;
            solidArea.height=solidAreaHeight;

        }
        if(spriteCounter>25){
            spritNum=1;
            spriteCounter=0;
            attacking=false;
        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            } else {
                attacking = true;
            }

        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                life -= 1;
            }
            invincible = true;
        }

    }
    public void damageMonster(int i){
        if(i!=999){
            System.out.println("hit");
        }else{
            System.out.println("miss");
        }
    }


    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image = null;
        int tempScreenX=screenX;
                int tempScreenY=screenY;
        switch (direction) {
            case "up":
                if(attacking==false) {
                    if (spritNum == 1) {
                        image = up1;
                    }
                    if (spritNum == 2) {
                        image = up2;
                    }
                }
                if(attacking==true){
                    tempScreenY=screenY-gp.tileSize;
                    if (spritNum == 1) {
                        image = Aup;
                    }
                    if (spritNum == 2) {
                        image = Aup;
                    }
                }
                break;
            case "down":
                if(attacking==false) {
                    if (spritNum == 1) {
                        image = down1;
                    }
                    if (spritNum == 2) {
                        image = down2;
                    }
                }
                if(attacking==true) {
                    if (spritNum == 1) {
                        image = Adown;
                    }
                    if (spritNum == 2) {
                        image = Adown;
                    }
                }
                break;
            case "left":
                if(attacking==false) {
                    if (spritNum == 1) {
                        image = left1;
                    }
                    if (spritNum == 2) {
                        image = left2;
                    }
                }
                if(attacking==true) {
                    tempScreenX=screenX-gp.tileSize;
                    if (spritNum == 1) {
                        image = Aleft;
                    }
                    if (spritNum == 2) {
                        image = Aleft;
                    }
                }
                break;
            case "right":
                if(attacking==false){  if (spritNum == 1) {image = right1;}
                    if (spritNum == 2) {image = right2;}
                  }
                if(attacking==true){  if (spritNum == 1) {image = Aright;}
                    if (spritNum == 2) {image = Aright;}
                    break;}
                }
                if(invincible==true){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

                //Reset alfa
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        //DEBUG
        //  g2.setFont(new Font("Arial",Font.PLAIN,26));
        // g2.setColor(Color.white);
        // g2.drawString("Invincible counter"+invincibleCounter,10,400);
    }
}
