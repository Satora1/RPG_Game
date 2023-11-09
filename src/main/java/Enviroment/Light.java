package Enviroment;

import Main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Light {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Light(GamePanel gp, int circleSize) {
//create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        //get the circle center x and y of the light
        int centerX = gp.player.screenX + (gp.tileSize) / 2;
        int centerY = gp.player.screenY + (gp.tileSize) / 2;

        //creat gradiation effect
        Color color[] = new Color[12];
        float fraction[] = new float[12];

        color[0] = new Color(0, 0, 0, 0.1f);
        color[1] = new Color(0, 0, 0, 0.42f);
        color[2] = new Color(0, 0, 0, 0.52f);
        color[3] = new Color(0, 0, 0, 0.61f);
        color[4] = new Color(0, 0, 0, 0.76f);
        color[5] = new Color(0, 0, 0, 0.82f);
        color[6] = new Color(0, 0, 0, 0.87f);
        color[7] = new Color(0, 0, 0, 0.92f);
        color[8] = new Color(0, 0, 0, 0.96f);
        color[9] = new Color(0, 0, 0, 0.97f);
        color[10] = new Color(0, 0, 0, 0.98f);
        color[11] = new Color(0, 0, 0, 0.99f);

        fraction[0] = 0.1f;
        fraction[1] = 0.3f;
        fraction[2] = 0.4f;
        fraction[3] = 0.5f;
        fraction[4] = 0.55f;
        fraction[5] = 0.6f;
        fraction[6] = 0.7f;
        fraction[7] = 0.75f;
        fraction[8] = 0.8f;
        fraction[9] = 0.85f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;
        //create a gradiation  paint settings for the light circle
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, circleSize / 2, fraction, color);
//set  the gradient data  on g2
        g2.setPaint(gPaint);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
