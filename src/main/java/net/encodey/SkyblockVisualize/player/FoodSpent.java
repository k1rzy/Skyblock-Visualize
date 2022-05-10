package net.encodey.SkyblockVisualize.player;

import net.encodey.SkyblockVisualize.core.TickEvent;
import net.encodey.SkyblockVisualize.core.interfaces.IPlayerEvent;
import net.encodey.SkyblockVisualize.core.interfaces.ITickEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FoodSpent implements ITickEvent, IPlayerEvent {

    protected static boolean standingStill() {
        return Minecraft.getMinecraft().thePlayer.motionX == 0.0 && Minecraft.getMinecraft().thePlayer.motionZ == 0.0;
    }

    @Override
    public double foodSpent() {
        if(Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null || standingStill()) {
            return 0;
        }
        if(ConfigStats.sprintLevel <= 1) return Minecraft.getMinecraft().thePlayer.isSprinting() ? 0.0034 : 0.00091;

        return Minecraft.getMinecraft().thePlayer.isSprinting() ? (0.003 / ConfigStats.sprintLevel) : (0.002 / ConfigStats.sprintLevel);
    }

    @Override
    public double healthSpent() {
        return 0;
    }

    @Override
    @SubscribeEvent
    public void onTick(TickEvent ticks) {
        ConfigStats.playerFood -= foodSpent();
    }
}
