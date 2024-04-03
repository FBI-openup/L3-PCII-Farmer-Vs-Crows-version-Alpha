package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
    * This class is used to play the crow eating sound.
*/
public class CrowEatingSoundThread {
    private Clip clip;
    private volatile boolean playing;
    private ScheduledExecutorService executorService;

    public CrowEatingSoundThread() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/chewing2 (1).wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            executorService = Executors.newSingleThreadScheduledExecutor();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playSound() {
        playing = true;
        executorService.scheduleAtFixedRate(() -> {
            if (playing) {
                clip.setFramePosition(0);
                clip.start();
            }
        }, 0, clip.getMicrosecondLength() / 1000, TimeUnit.MICROSECONDS);
    }

    public void stopSound() {
        playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }
}