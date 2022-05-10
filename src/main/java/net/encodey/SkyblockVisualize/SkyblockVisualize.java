package net.encodey.SkyblockVisualize;

import net.encodey.SkyblockVisualize.core.commands.DeleteProfileCommand;
import net.encodey.SkyblockVisualize.core.commands.NewProfileCommand;
import net.encodey.SkyblockVisualize.core.commands.OpenMainGuiCommand;
import net.encodey.SkyblockVisualize.player.FoodSpent;
import net.encodey.SkyblockVisualize.player.GetterStats;
import net.encodey.SkyblockVisualize.player.LevelProfiler;
import net.encodey.SkyblockVisualize.player.StatSaver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod(modid = SkyblockVisualize.MODID, version = SkyblockVisualize.VERSION)
public class SkyblockVisualize
{
    public static final String MODID = "skyblockVisualize";
    public static final String VERSION = "1.0.0.0";

    @Mod.Instance("skyblockvisualize")
    public static SkyblockVisualize INSTANCE;
    public static String prefix = EnumChatFormatting.BOLD + (" " + EnumChatFormatting.GOLD
            + EnumChatFormatting.BOLD
            + EnumChatFormatting.GOLD
            + "+ ")
            + EnumChatFormatting.YELLOW;

    public GuiScreen currentScreen;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        registerEvent(new Walkthrough());
        registerEvent(new FoodSpent());
        registerEvent(new LevelProfiler());

        GetterStats.reload();
    }

    private static void registerEvent(Object o) {
        MinecraftForge.EVENT_BUS.register(o);
    }

    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event)
    {
        INSTANCE = this;
        Display.setTitle("Skyblock visualize v" + VERSION + " - Minecraft "
        + Minecraft.getMinecraft().getVersion());
    }

    @Mod.EventHandler
    public void postInit(FMLInitializationEvent event)
    {
        MixinBootstrap.init();
        ClientCommandHandler.instance.registerCommand(new NewProfileCommand());
        ClientCommandHandler.instance.registerCommand(new OpenMainGuiCommand());
        ClientCommandHandler.instance.registerCommand(new DeleteProfileCommand());
        GetterStats.init();
    }

    public int tickAmount = 1;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Minecraft.getMinecraft().theWorld == null || event.phase != TickEvent.Phase.START) return;
        MinecraftForge.EVENT_BUS.post(new net.encodey.SkyblockVisualize.core.TickEvent());

        tickAmount++;
        if (tickAmount % 20 == 0) {
            if (Minecraft.getMinecraft().thePlayer != null) {
                new StatSaver();
            }

            tickAmount = 0;
        }
        if(currentScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(currentScreen);
            currentScreen = null;
        }
    }
}
