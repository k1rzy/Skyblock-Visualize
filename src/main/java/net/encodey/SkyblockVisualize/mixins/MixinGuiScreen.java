package net.encodey.SkyblockVisualize.mixins;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={GuiScreen.class})
public class MixinGuiScreen {
    @Shadow
    public Minecraft mc;
    @Shadow
    protected List<GuiButton> buttonList;
    @Shadow
    public int width;
    @Shadow
    public int height;
}
