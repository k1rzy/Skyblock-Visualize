package net.encodey.SkyblockVisualize.player;

import net.encodey.SkyblockVisualize.SkyblockVisualize;
import net.encodey.SkyblockVisualize.core.TickEvent;
import net.encodey.SkyblockVisualize.core.interfaces.ITickEvent;
import net.encodey.SkyblockVisualize.events.SoundEvent;
import net.encodey.SkyblockVisualize.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.text.DecimalFormat;

public class LevelProfiler implements ITickEvent {

    private Minecraft mc = Minecraft.getMinecraft();

    public static double sprintingLevelExp;

    @Override
    @SubscribeEvent
    public void onTick(TickEvent ticks) {
        if(SkyblockVisualize.INSTANCE.tickAmount % 20 != 0) return;

        if(mc.thePlayer.isSprinting()) {
            sprintingLevelExp += (ConfigStats.sprintLevel <= 1) ? 0.002 : (0.009 / ConfigStats.sprintLevel) / ConfigStats.sprintLevel;
            if(sprintingLevelExp >= 1) {
                sprintingLevelExp = 0;
                ConfigStats.sprintLevel++;
                msgLevelUp(
                        "Sprinting skill level up! Current level: "
                        + ConfigStats.sprintLevel
                        + "\n "
                        + EnumChatFormatting.GRAY
                        + "You feel like your hunger is satisfied...");
                ConfigStats.playerMaxFood += 2.0;
                ConfigStats.playerFood += 4.4;
            }
        }
    }

    private void msgLevelUp(String text) {
        mc.thePlayer.addChatMessage(new ChatComponentText(SkyblockVisualize.prefix + text));
        new SoundEvent(new File("config/svdata/skill_level_up.wav"));
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
            DecimalFormat format = new DecimalFormat("0.##");

            GlStateManager.enableAlpha();
            if(mc.thePlayer.isSprinting()) {
                GL11.glScaled(1,1,1);

                mc.getTextureManager().bindTexture(LibResources.SPRINTING_SKILL);
                Gui.drawModalRectWithCustomSizedTexture(27, 5, 0, 0, 24, 16, 24, 16);
                GlStateManager.color(1, 1, 1, 0.4f);
                GL11.glColor4f(1,1,1, 0.4f);
                GlStateManager.enableLight(3);
                new TextUtils(mc, EnumChatFormatting.BOLD + (EnumChatFormatting.GREEN + " " + EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + "Sprinting " + format.format(sprintingLevelExp)), 65, 5, 1, 0.4f,  false);
            }
        }
    }
}
