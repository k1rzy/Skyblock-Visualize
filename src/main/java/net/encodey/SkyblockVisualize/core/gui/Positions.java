package net.encodey.SkyblockVisualize.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Positions {
    static ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    static int width = scaledResolution.getScaledWidth();
    static int height = scaledResolution.getScaledHeight();

    // x - minus = left
    // x - plus = right
    // y - minus = up
    // y - plus = down

    public static final int TOP_LEFT_XW = 25;
    public static final int TOP_LEFT_YH = (height + (height - 5)) - 950;

    public static final int BOTTOM_CENTERED_XW = width / 2 - 56;
    public static final int BOTTOM_CENTERED_YH = (height * 2) + 252;
}
