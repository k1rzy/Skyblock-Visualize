package net.encodey.SkyblockVisualize.core.gui;

import net.encodey.SkyblockVisualize.player.LibResources;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class CraftingTableButton extends GuiButton {
    public CraftingTableButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            mc.getTextureManager().bindTexture(LibResources.CRAFTING_TABLE_2D);

            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            //drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? new Color(220, 132, 57, 74).getRGB() : new Color(169, 99, 39, 77).getRGB());
            drawTexturedModalRect(this.xPosition, this.yPosition, 17, 17, 17, 17);

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;
            if (this.packedFGColour != 0) {
                j = this.packedFGColour;
            }
        }
    }
}

