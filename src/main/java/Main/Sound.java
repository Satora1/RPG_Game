package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURl[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundURl[0] = getClass().getResource("/sound/main.wav");
        soundURl[1] = getClass().getResource("/sound/win.wav");
        soundURl[2] = getClass().getResource("/sound/picking_item.wav");
        soundURl[3] = getClass().getResource("/sound/damage-1.wav");
        soundURl[4] = getClass().getResource("/sound/dying.wav");
        soundURl[5] = getClass().getResource("/sound/swing-sword.wav");
        soundURl[6] = getClass().getResource("/sound/lvl_up.wav");
        soundURl[7] = getClass().getResource("/sound/inventory_click.wav");
        soundURl[8] = getClass().getResource("/sound/fireBall.wav");
        soundURl[9] = getClass().getResource("/sound/lose.wav");
        soundURl[10] = getClass().getResource("/sound/dor.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkeVolume();
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

    public void checkeVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }
        fc.setValue(volume);
    }
}
