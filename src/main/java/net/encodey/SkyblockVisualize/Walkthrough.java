package net.encodey.SkyblockVisualize;

import net.encodey.SkyblockVisualize.core.TickEvent;
import net.encodey.SkyblockVisualize.core.interfaces.ITickEvent;
import net.encodey.SkyblockVisualize.utils.RenderScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class Walkthrough implements ITickEvent {

    private boolean joined;

    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.joined = true;
    }

    @Override
    @SubscribeEvent
    public void onTick(TickEvent event) {
        String textNew = "game, ";
        if(this.joined) {
            this.joined = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.
                    LIGHT_PURPLE + ("To start a new " + textNew.toString() + String.format("", "\n\n") +
                    "type command /newprofile or /npStart")));
            Minecraft.getMinecraft().thePlayer.playSound("dig.snow", 1, 1);
        }
    }
}
