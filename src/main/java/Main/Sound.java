package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURl[] = new URL[30];

    public Sound() {
        soundURl[0] = getClass().getResource("/sound/main.wav");
        soundURl[1] = getClass().getResource("/sound/win.wav");
        soundURl[2] = getClass().getResource("/sound/picking_item.wav");
        soundURl[3] = getClass().getResource("/sound/damage-1.wav");
        soundURl[4] = getClass().getResource("/sound/dying.wav");
        soundURl[5] = getClass().getResource("/sound/swing-sword.wav");
        soundURl[6]=getClass().getResource("/sound/lvl_up.wav");
        soundURl[7]=getClass().getResource("/sound/inventory_click.wav");
        soundURl[8]=getClass().getResource("/sound/fireBall.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
