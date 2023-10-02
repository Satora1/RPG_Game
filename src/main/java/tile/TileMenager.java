package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMenager {
    GamePanel gp;
    public Tile[] tile;

    public int mapTileNumber[][][];

    public TileMenager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNumber = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map.txt",0);
        loadMap("/maps/mapHouse.txt",1);
    }

    public void getTileImage() {


        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "sand", false);
        setup(4, "earth", false);
        setup(5, "wood", true);
        setup(6, "PIT", false);
        setup(7, "Hflor", false);
        setup(8, "House", false);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath,int map) {
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
        int worldCol = 0;
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
    }


}



