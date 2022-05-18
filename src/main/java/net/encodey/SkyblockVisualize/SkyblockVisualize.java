package net.encodey.SkyblockVisualize;

import net.encodey.SkyblockVisualize.core.commands.DeleteProfileCommand;
import net.encodey.SkyblockVisualize.core.commands.NewProfileCommand;
import net.encodey.SkyblockVisualize.core.commands.OpenMainGuiCommand;
import net.encodey.SkyblockVisualize.player.*;
import net.encodey.SkyblockVisualize.sounds.GetterLocation;
import net.encodey.SkyblockVisualize.sounds.SoundLocation;
import net.encodey.SkyblockVisualize.sounds.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod(modid = SkyblockVisualize.MODID, version = SkyblockVisualize.VERSION)
public class SkyblockVisualize
{
    public static final String MODID = "skyblockVisualize";
    public static final String VERSION = "1.0.0.0";

    @Mod.Instance("skyblockvisualize")
    public static SkyblockVisualize INSTANCE;
    public static final String prefix = EnumChatFormatting.BOLD + (" " + EnumChatFormatting.GOLD
            + EnumChatFormatting.BOLD
            + EnumChatFormatting.GOLD
            + "+ ")
            + EnumChatFormatting.YELLOW;

    public GuiScreen currentScreen;

    public final KeyBinding[] keyBindings = new KeyBinding[1];

    public boolean debugMode;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        registerEvent(new Walkthrough());
        registerEvent(new FoodSpent());
        registerEvent(new LevelProfiler());
        registerEvent(new GetterLocation());
        registerEvent(new SoundLocation());
        registerEvent(new SoundManager());
        registerEvent(new PlayerHitSoundTrigger());

        GetterStats.reload();

        keyBindings[0] = new KeyBinding("Debug mode", Keyboard.KEY_NONE, "Skyblock Visualize");

        for(KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
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
                if(debugMode) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                            new ChatComponentText(
                                    "Save complete")
                    );
                }
            }

            tickAmount = 0;
        }
        if(currentScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(currentScreen);
            currentScreen = null;
        }
    }

    @SubscribeEvent
    public void keyPress(InputEvent.KeyInputEvent event) {
        if(keyBindings[0].isPressed()) debugMode = !debugMode;
    }
}
