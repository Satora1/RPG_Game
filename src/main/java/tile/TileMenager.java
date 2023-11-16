package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileMenager {
    GamePanel gp;
    public Tile[] tile;

    public int mapTileNumber[][][];
    public boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisonStatus = new ArrayList<>();

    public TileMenager(GamePanel gp) {
        this.gp = gp;
        //read tile data file
        InputStream is = getClass().getResourceAsStream("/maps/tileData");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //geting  tile name  and collision  info from file
        String line;
        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisonStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();


        //get max col and row
        is = getClass().getResourceAsStream("/maps/Main.map");
        br = new BufferedReader(new InputStreamReader(is));
        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");
            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNumber = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
            br.close();
        } catch (IOException e) {
            System.out.println("Exp");
        }
loadMap("/maps/Main.map",0);

      //  loadMap("/maps/map.txt", 0);
        loadMap("/maps/shop", 1);
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;
            //get file name
            fileName = fileNames.get(i);
            //get colssion status
            if (collisonStatus.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }
            setup(i, fileName, collision);
        }

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[map][col][row] = num;
                    col++;


                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }

        } catch (
                Exception e
        ) {

        }
    }

    public void draw(Graphics2D g2) {
        int worldCol;
        int worldRow = 0;

        while (worldRow < gp.maxWorldRow) {
            worldCol = 0;
            while (worldCol < gp.maxWorldCol) {
                int tileNum = mapTileNumber[gp.currnetMap][worldCol][worldRow];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (screenX >= -gp.tileSize && screenX <= gp.getPanelWidth() &&
                        screenY >= -gp.tileSize && screenY <= gp.getPanelHeight()) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }

                worldCol++;
            }
            worldRow++;
        }
        if (drawPath == true) {
            g2.setColor(new Color(250, 0, 0, 70));
            for (int i = 0; i < gp.pathFinder.pathList.size(); i++) {

                int worldX = gp.pathFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pathFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }


}



