package net.encodey.SkyblockVisualize.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiInventory.class})
public class MixinGuiInventory extends MixinGuiScreen {

    private static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    private static void drawString(String text, int x, int y, int color) {
        fr.drawString(text, x, y, color, true);
    }

    private static int getStringWidth(String text) {
        return fr.getStringWidth(text);
    }

    private static int getFontHeight() {
        return fr.FONT_HEIGHT;
    }

    @Inject(method={"initGui"}, at={@At(value="RETURN")})
    private void initGui(CallbackInfo ci) {
        this.buttonList.add(new GuiButton(998, this.width - 150, 6, 25, 25, EnumChatFormatting.BOLD + "S"));
    }

    @Inject(method={"drawScreen"}, at={@At(value="RETURN")})
    private void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        drawString("Profile", this.width - 150, 30, -1);
    }
}
