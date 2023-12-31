package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

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

        //title state
        if (gp.gameState == gp.titleState) {

            titleState(code);

        }
        //PLAY STATE

        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseGame) {
            pauseState(code);
        }
        //Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);

        }
        //CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterSate(code);
        }
        //Option STATE
        else if (gp.gameState == gp.optionsState) {
            optionSate(code);
        }
        //Game over STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverSate(code);
        }
        //Trade STATE
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }
        //Map state
        else if (gp.gameState == gp.mapState) {
            mapState(code);
        }

    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
                gp.playSE(7);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
                gp.playSE(7);
            }

        }
        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }

        }
        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }

        }
    }

    public void optionSate(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 5;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            gp.playSE(7);
            if (gp.ui.commandNumber > maxCommandNum) {
                gp.ui.commandNumber = 0;
            }
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            gp.playSE(7);
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkeVolume();
                    gp.playSE(7);
                }
                if (gp.ui.commandNumber == 2 && gp.soundEffect.volumeScale > 0) {
                    gp.soundEffect.volumeScale--;
                    gp.playSE(7);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkeVolume();
                    gp.playSE(7);
                }
                if (gp.ui.commandNumber == 2 && gp.soundEffect.volumeScale < 5) {
                    gp.soundEffect.volumeScale++;
                    gp.playSE(7);
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
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }

    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNumber == 0) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (gp.ui.commandNumber == 1) {
                //later
            }
            if (gp.ui.commandNumber == 2) {
                System.exit(0);
            }
        }
    }

    public void playState(int code) {
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
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }
        if (code == KeyEvent.VK_X) {
            if (gp.map.miniMapOn == false) {
                gp.map.miniMapOn = true;
            } else {
                gp.map.miniMapOn = false;
            }
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

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }

    }

    public void characterSate(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }


        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotPlayerRow != 0) {
                gp.ui.slotPlayerRow--;
                gp.playSE(7);
            }

        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.sloPlayertCol != 0) {
                gp.ui.sloPlayertCol--;
                gp.playSE(7);
            }

        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotPlayerRow != 3) {
                gp.ui.slotPlayerRow++;
                gp.playSE(7);
            }

        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.sloPlayertCol != 4) {
                gp.ui.sloPlayertCol++;
                gp.playSE(7);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotNPCRow != 0) {
                gp.ui.slotNPCRow--;
                gp.playSE(7);
            }

        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotNPCCol != 0) {
                gp.ui.slotNPCCol--;
                gp.playSE(7);
            }

        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotNPCRow != 3) {
                gp.ui.slotNPCRow++;
                gp.playSE(7);
            }

        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotNPCCol != 4) {
                gp.ui.slotNPCCol++;
                gp.playSE(7);
            }
        }
    }

    public void gameOverSate(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 1;
            }
            gp.playSE(7);

        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            if (gp.ui.commandNumber > 1) {
                gp.ui.commandNumber = 0;
            }
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNumber == 0) {
                gp.gameState = gp.playState;
                gp.retry();
            } else if (gp.ui.commandNumber == 1) {
                gp.gameState = gp.titleState;
                gp.stopMusic();
                gp.restart();
            }
        }

    }

    public void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }

}
