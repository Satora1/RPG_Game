package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    //boolean for DEBUG
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nie używamy
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseGame;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }


            //DEBUG
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                } else if (checkDrawTime == true) {
                    checkDrawTime = false;
                }

            }
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseGame) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        //Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }

        }
        //title state
        if(gp.gameState==gp.titleState){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
            }
            if (code == KeyEvent.VK_S) {
               gp.ui.commandNumber++;
            }
            if(code==KeyEvent.VK_ENTER){
                if(gp.ui.commandNumber==0){
                    gp.gameState=gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNumber==1){
                    //later
                }
                if(gp.ui.commandNumber==2){
                    System.exit(0);
                }
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }


    }
}
