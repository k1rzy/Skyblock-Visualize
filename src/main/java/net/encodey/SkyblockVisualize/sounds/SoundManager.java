package net.encodey.SkyblockVisualize.sounds;

import net.encodey.SkyblockVisualize.core.TickEvent;
import net.encodey.SkyblockVisualize.core.interfaces.ITickEvent;
import net.encodey.SkyblockVisualize.events.SoundEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class SoundManager implements ITickEvent {

    private int saveFrame;

    private boolean paused;

    private File saveFile;

    @Override
    @SubscribeEvent
    public void onTick(TickEvent ticks) {
        if(SoundEvent.clip == null
        || SoundEvent.soundFile == null) return;

        if(Minecraft.getMinecraft().isGamePaused() || Minecraft.getMinecraft().theWorld == null) {
            paused = true;
            saveFile = SoundEvent.soundFile;
            saveFrame = SoundEvent.clip.getFramePosition();
            SoundEvent.clip.stop();
        }

        if(paused && (!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().theWorld != null)) {
            paused = false;
            new SoundEvent(saveFile);
            SoundEvent.clip.setFramePosition(saveFrame);
            saveFrame = 0;
            saveFile = null;
        }
    }
}
