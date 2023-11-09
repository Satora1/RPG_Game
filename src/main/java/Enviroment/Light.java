package Enviroment;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Light {
  public   int dayCounter;
   public float filterAlpha=0f;
   public final int day=0;
   public final int dusk=1;
   public final int night=2;
   public final int dawn=3;
   public int dayState=day;
    GamePanel gp;
    BufferedImage darknessFilter;

    public Light(GamePanel gp) {
this.gp=gp;
setLightSource();
    }
public void setLightSource(){
    //create a buffered image
    darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
   if(gp.player.currentLight==null){
       g2.setColor(new Color(0,0,0.1f,0.98f));
   }
   else{
       //get the circle center x and y of the light
       int centerX = gp.player.screenX + (gp.tileSize) / 2;
       int centerY = gp.player.screenY + (gp.tileSize) / 2;

       //creat gradiation effect
       Color color[] = new Color[12];
       float fraction[] = new float[12];

       color[0] = new Color(0, 0, 0.1f, 0.1f);
       color[1] = new Color(0, 0, 0.1f, 0.42f);
       color[2] = new Color(0, 0, 0.1f, 0.52f);
       color[3] = new Color(0, 0, 0.1f, 0.61f);
       color[4] = new Color(0, 0, 0.1f, 0.76f);
       color[5] = new Color(0, 0, 0.1f, 0.82f);
       color[6] = new Color(0, 0, 0.1f, 0.87f);
       color[7] = new Color(0, 0, 0.1f, 0.92f);
       color[8] = new Color(0, 0, 0.1f, 0.96f);
       color[9] = new Color(0, 0, 0.1f, 0.97f);
       color[10] = new Color(0, 0, 0.1f, 0.98f);
       color[11] = new Color(0, 0, 0.1f, 0.99f);

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
       RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);
//set  the gradient data  on g2
       g2.setPaint(gPaint);
   }

    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    g2.dispose();
}
public void update(){
        if(gp.player.lightUpdated==true) {
            setLightSource();
            gp.player.lightUpdated = false;
        }
            //check the state of the day
            if(dayState==day){
                dayCounter++;
                if(dayCounter>600){
                    //36000==10min
                    dayState=dusk;
                    dayCounter=0;
                }
            }
            if(dayState==dusk){
                filterAlpha+=0.001f;
                if(filterAlpha>1f){
                    filterAlpha=1;
                    dayState=night;
                }
            }
            if(dayState==night){
                dayCounter++;
                if(dayCounter>600){
                    dayState=dawn;
                    dayCounter=0;
                }
            }
            if(dayState==dawn){
                filterAlpha-=0.001f;
                if(filterAlpha<0f){
                  filterAlpha=0;
                  dayState=day;
                }
            }
        }

    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        //debug
        String situation="";
        switch (dayState){
            case day:situation="day";break;
            case dusk:situation="dusk";break;
            case night:situation="night";break;
            case dawn:situation="dawn";break;
        }
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawString(situation,800,500);
    }
}
