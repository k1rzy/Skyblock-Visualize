package net.encodey.SkyblockVisualize.events;

import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEvent {
    public SoundEvent(File sound) {
        try {
            Clip clip = AudioSystem.getClip();
            if(clip.isRunning()) {
                clip.stop();
                clip.setFramePosition(0);
            }
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
