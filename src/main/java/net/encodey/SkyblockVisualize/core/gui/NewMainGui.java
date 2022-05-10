package net.encodey.SkyblockVisualize.core.gui;

import net.encodey.SkyblockVisualize.utils.ConstructFontTexturedUtil;
import net.encodey.SkyblockVisualize.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.text.DecimalFormat;

public class NewMainGui extends GuiScreen implements GuiVisualize {

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        this.buttonList.add(new CraftingTableButton(0, width / 2 + 100, height / 4 + 160, 48, 48, ""));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        Minecraft mc = Minecraft.getMinecraft();

        super.drawDefaultBackground();

        DecimalFormat decimalFormat = new DecimalFormat("0.#");

        GlStateManager.scale(1.0, 1.0, 0.0);

        new TextUtils(mc, EnumChatFormatting.RED + "Current health - "
                + EnumChatFormatting.GREEN
                + decimalFormat.format(PLAYER_HEALTH())
                + EnumChatFormatting.LIGHT_PURPLE
                + "/"
                + EnumChatFormatting.GREEN
                + decimalFormat.format(PLAYER_MAX_HEALTH()), Positions.TOP_LEFT_XW, Positions.TOP_LEFT_YH + 50, 1, true);

        new TextUtils(mc, EnumChatFormatting.RED + "Current defense - "
                + EnumChatFormatting.GREEN
                + PLAYER_DEFENSE()
                , Positions.TOP_LEFT_XW, Positions.TOP_LEFT_YH + 258, 1, true);

        new TextUtils(mc, EnumChatFormatting.RED + "Food - "
                + EnumChatFormatting.GREEN
                + decimalFormat.format(PLAYER_FOOD()), Positions.TOP_LEFT_XW, Positions.TOP_LEFT_YH + 125, 1, true);

        new TextUtils(mc, EnumChatFormatting.RED + "Selected item - "
                + EnumChatFormatting.GREEN
                + SELECTED_ITEM(), Positions.BOTTOM_CENTERED_XW + 5, Positions.BOTTOM_CENTERED_YH - 1, 1, false);

        new ConstructFontTexturedUtil(mc, EnumChatFormatting.AQUA + "Platina - "
                + EnumChatFormatting.BLUE
                + decimalFormat.format(PLAYER_PURSE())
                , Positions.TOP_LEFT_XW
                , Positions.TOP_LEFT_YH + 190
                , 1
                , new ResourceLocation("svtextures", "textures/stat_textures/platina.png"), 16, 16
                , Positions.TOP_LEFT_XW
                , -(Positions.TOP_LEFT_YH + 190)
                , true);

        GlStateManager.enableDepth();

        super.drawScreen(mouseX, mouseY, partialTicks);

        for (final GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }
}
