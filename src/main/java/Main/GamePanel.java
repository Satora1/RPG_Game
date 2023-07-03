package Main;

import entity.Entity;
import entity.Player;
import tile.TileMenager;

import javax.swing.*;
import java.awt.*;
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
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;//768
    public final int screenHeight = tileSize * maxScreenRow;//576
    //FPs
    int FPS = 60;
    //World map mapramiters
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    //System
    TileMenager tileM = new TileMenager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    public UI ui = new UI(this);
    public Eventhandler eHandler = new Eventhandler(this);
    Thread gameThread;
    public CollisonChecker checker = new CollisonChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);

    //Entity and Objects
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];//ten slots for object, dispaly 10 obejct in game in same time
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entitiesList = new ArrayList<>();
    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseGame = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;


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

       // playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    //second resolve
    public void run() {
        double drawInterwal = 1000000000 / FPS;
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
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS" + drawCount);

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
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }

            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if (monster[i].alive != true) {
                        monster[i] = null;
                    }

                }
            }
        }
        if (gameState == pauseGame) {
            //nothing when pause
        }


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


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
//add entity to list
            tileM.draw(g2);
            entitiesList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entitiesList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entitiesList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entitiesList.add(monster[i]);
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
            System.out.println("draw time" + passed);
        }


        g2.dispose();
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


}
