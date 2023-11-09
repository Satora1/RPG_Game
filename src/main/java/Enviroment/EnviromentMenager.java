package Enviroment;

import Main.GamePanel;

import java.awt.*;

public class EnviromentMenager {
    GamePanel gp;
    Light light;
    public EnviromentMenager(GamePanel gp){
        this.gp=gp;
    }
    public void setup(){
        light =new Light(gp);
    }
    public void update(){
        light.update();
    }
    public void draw(Graphics2D g2){
        light.draw(g2);
    }
}
