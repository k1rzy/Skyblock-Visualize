package net.encodey.SkyblockVisualize.core.commands;

import net.encodey.SkyblockVisualize.SkyblockVisualize;
import net.encodey.SkyblockVisualize.core.BooleanCooldown;
import net.encodey.SkyblockVisualize.core.gui.NewMainGui;
import net.encodey.SkyblockVisualize.events.SoundEvent;
import net.encodey.SkyblockVisualize.player.GetterStats;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;

import java.io.File;

public class NewProfileCommand extends CommandManager {

    public static boolean createdProfile;

    @Override
    String commandName() {
        return "newprofile";
    }

    @Override
    String commandAlias() {
        return "npstart";
    }

    @Override
    public void processCommand() {
        if(createdProfile) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED
                        + "Cannot start a new profile with already existing profile. "
                        +
                        "To save existing profile, copy svProfile1 " + String.format("", "\n\n")
                        + "file from Config folder and keep it. "
                        +
                        String.format("", "\n\n") + "Command to delete profile - /deleteprofile"));
            File buf = new File("config/svdata/sounds/error_sound.wav");
            new SoundEvent(buf);
            BooleanCooldown.getCooldown().set_newProfileCommandUsed(true);
            return;
        }
        Minecraft.getMinecraft().thePlayer.playSound("mob.enderdragon.growl", 1.0f, 1.0f);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                EnumChatFormatting.BOLD
                        + (EnumChatFormatting.AQUA
                        + "  Thanks for playing my mod!\n ")
                        + EnumChatFormatting.GREEN
                        + " For better experience start a new skyblock profile, to get a lot of game content!"));
        BooleanCooldown.getCooldown().set_newProfileCommandUsed(false);
        SkyblockVisualize.INSTANCE.currentScreen = new NewMainGui();
        createdProfile = true;
        GetterStats.saveStat("main", "created_profile", true);
    }
}
