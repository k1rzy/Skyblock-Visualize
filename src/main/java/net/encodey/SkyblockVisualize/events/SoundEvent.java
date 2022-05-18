package net.encodey.SkyblockVisualize.events;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEvent {

    public static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static File soundFile;

    public SoundEvent(File sound) {
        soundFile = sound;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
