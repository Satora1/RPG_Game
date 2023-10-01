package Main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
//full screen setting
            if(gp.fullScreenOn==true){
                bw.write("On");
            }
            if(gp.fullScreenOn==false){
                bw.write("Off");
            }
            bw.newLine();
            //Music vol
            bw.write(String.valueOf(gp.music.volumeScale));
            //SE vol
            bw.newLine();
            bw.write(String.valueOf(gp.soundEffect.volumeScale));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try{
        BufferedReader br =new BufferedReader(new FileReader("config.txt"));
        String s =br.readLine();
        // full screen
            if(s.equals("On")){
                gp.fullScreenOn=true;
            }
            if(s.equals("Off")){
                gp.fullScreenOn=false;
            }
            //Music
            s=br.readLine();
            gp.music.volumeScale= Integer.parseInt(s);
            //SE
            s=br.readLine();
            gp.soundEffect.volumeScale= Integer.parseInt(s);
            br.close();
    }catch (IOException e){
            e.printStackTrace();
        }
    }
}
