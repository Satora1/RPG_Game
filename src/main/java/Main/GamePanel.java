package Main;

import AI.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileMenager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //screen settings

    public int getPanelWidth() {
        return this.getWidth();
    }

    public int getPanelHeight() {
        return this.getHeight();
    }

    final int orginalTitleSize = 16;//16x16 title
    final int scale = 3;
    public final int tileSize = orginalTitleSize * scale;//48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;//768
    public final int screenHeight = tileSize * maxScreenRow;//576
    //FPs
    int FPS = 60;
    //World map mapramiters
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currnetMap = 0;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    //Full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempscreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;


    //System
    public TileMenager tileM = new TileMenager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    public PathFinder pathFinder = new PathFinder(this);
    Sound soundEffect = new Sound();
    Config config = new Config(this);

    public UI ui = new UI(this);
    public Eventhandler eHandler = new Eventhandler(this);
    Thread gameThread;
    public CollisonChecker checker = new CollisonChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);

    //Entity and Objects
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];//ten slots for object, dispaly 10 obejct in game in same time
    public Entity npc[][] = new Entity[maxMap][20];
    public Entity monster[][] = new Entity[maxMap][20];

    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entitiesList = new ArrayList<>();
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseGame = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();
        aSetter.setNPC();
        aSetter.setInteractiveTile();

        // playMusic(0);
        gameState = titleState;
        tempscreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempscreen.getGraphics();
        if (fullScreenOn == true) {
            setFullScreen();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    //second resolve
    public void run() {
        double drawInterwal = 1000000000 / (FPS);
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterwal;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                try {
                    drawToTempScreen();//draw evrything to buffer image
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                drawToScreen();//draw buffer image to screen
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {


                drawCount = 0;
                timer = 0;
            }
        }
    }


    public void update() {
        if (gameState == playState) {
            //PLAYER
            player.update();
            //NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currnetMap][i] != null) {
                    npc[currnetMap][i].update();
                }

            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currnetMap][i] != null) {
                    if (monster[currnetMap][i].alive == true && monster[currnetMap][i].dying == false) {
                        monster[currnetMap][i].update();
                    }
                    if (monster[currnetMap][i].alive == false) {
                        monster[currnetMap][i].checkDrop();
                        monster[currnetMap][i] = null;
                    }

                }
            }


            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currnetMap][i] != null) {
                    if (projectile[currnetMap][i].alive == true) {
                        projectile[currnetMap][i].update();
                    }
                    if (projectile[currnetMap][i].alive != true) {
                        projectile[currnetMap][i] = null;
                    }

                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive != true) {
                        particleList.remove(i);
                    }

                }
            }
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currnetMap][i] != null) {
                    iTile[currnetMap][i].update();
                }
            }
        }
        if (gameState == pauseGame) {
            //nothing when pause
        }


    }

    public void drawToTempScreen() throws IOException {
        //DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }
//TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        //OTHERS
        else {//TILE
            tileM.draw(g2);
            //INTERACTIVE TILES

            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currnetMap][i] != null) {
                    iTile[currnetMap][i].draw(g2);
                }
            }

            //add entity to list
            entitiesList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currnetMap][i] != null) {
                    entitiesList.add(npc[currnetMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currnetMap][i] != null) {
                    entitiesList.add(obj[currnetMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currnetMap][i] != null) {
                    entitiesList.add(monster[currnetMap][i]);
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currnetMap][i] != null) {
                    entitiesList.add(projectile[currnetMap][i]);
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entitiesList.add(particleList.get(i));
                }
            }
//SORT
            Collections.sort(entitiesList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
//Draw entities
            for (int i = 0; i < entitiesList.size(); i++) {
                entitiesList.get(i).draw(g2);
            }
//Empty entities list
            entitiesList.clear();
//UI
            ui.draw(g2);
        }


        //DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("draw time " + passed, 10, 400);
            g2.drawString("X " + (player.worldX) / tileSize, 10, 350);
            g2.drawString("Y " + (player.worldY) / tileSize, 10, 300);
            System.out.println("draw time" + passed);

        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempscreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void setFullScreen() {
        //Get local  screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        //Get full screen width and height
        screenHeight2 = Main.window.getHeight();
        screenWidth2 = Main.window.getWidth();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void retry() {
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setMonster();
        aSetter.setNPC();
    }

    public void restart() {
        player.setDeflautValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setMonster();
        aSetter.setNPC();
        aSetter.setInteractiveTile();
    }

}
