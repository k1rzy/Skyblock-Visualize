package net.encodey.SkyblockVisualize.sounds;

import net.encodey.SkyblockVisualize.core.BooleanCooldown;
import net.encodey.SkyblockVisualize.core.TickEvent;
import net.encodey.SkyblockVisualize.core.interfaces.ITickEvent;
import net.encodey.SkyblockVisualize.events.SoundEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundLocation implements ITickEvent {

    private boolean running;

    private boolean playingInTheHub;

    private String soundtrack;

    @Override
    @SubscribeEvent
    public void onTick(TickEvent ticks) {
        if(Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().isGamePaused()) return;
        if(running) {

            if(soundtrack == null) return;
            if(playingInTheHub
                    && !BooleanCooldown.getCooldown().is_soundLocationUsed()
            ) {
                switch (soundtrack) {
                    case "the_hub.wav": {
                        BooleanCooldown.getCooldown().set_soundLocationUsed(true);
                        new SoundEvent(new File("config/svdata/soundtracks/the_hub.wav"));
                    }
                }
            }
        }

        if(
                soundtrack == null &&
                playingInTheHub
        ){
            if(SoundEvent.clip == null) return;
            FloatControl volume = (FloatControl) SoundEvent.clip.getControl(FloatControl.Type.MASTER_GAIN);
            for(int i = 100; i > 0; i--) {
                int finalI = i;
                new Thread(() -> {
                    try {
                        Thread.sleep(25);
                        volume.setValue(volume.getValue() - finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();
            }
            running = false;
            SoundEvent.clip.stop();
        }

        if(GetterLocation.getLocation() == GetterLocation.Location.HUB) {
            playingInTheHub = true;
            soundtrack = "the_hub.wav";
            running = true;
        }
        else {
            BooleanCooldown.getCooldown().set_soundLocationUsed(false);
            soundtrack = null;
            running = false;
        }
    }
}
