package net.encodey.SkyblockVisualize.player;

import net.encodey.SkyblockVisualize.core.TickEvent;
import net.encodey.SkyblockVisualize.core.interfaces.ITickEvent;
import net.encodey.SkyblockVisualize.screen.BlurManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerHitSoundTrigger implements ITickEvent {

    @Override
    @SubscribeEvent
    public void onTick(TickEvent ticks) {
        if(Minecraft.getMinecraft().thePlayer.hurtResistantTime != 0) {
            Minecraft.getMinecraft().thePlayer.playSound("mob.enderdragon.hit", 1.0f, 1.0f);
            BlurManager.playHurtAnimation();
        }
    }
}
