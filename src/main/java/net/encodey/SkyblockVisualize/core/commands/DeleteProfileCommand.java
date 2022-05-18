package net.encodey.SkyblockVisualize.core.commands;

import net.encodey.SkyblockVisualize.player.GetterStats;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.File;

public class DeleteProfileCommand extends CommandManager {

    private boolean sure;

    @Override
    String commandName() {
        return "deleteprofile";
    }

    @Override
    String commandAlias() {
        return "delprof";
    }

    @Override
    public void processCommand() {
        if(sure) {
            File profile = new File("config/svProfile1.cfg");
            try {
                profile.delete();
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.GREEN + "Successfully deleted profile. Now, restart minecraft to reset all settings in the mod."));
                GetterStats.init();
                GetterStats.reload();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Caught exception while removing profile. Make sure thats file is in config folder, and is named svProfile1"));
            }
            return;
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText(
                        EnumChatFormatting.RED
                        + "Are you sure? You still can save existing profile," +
                          " from command /newprofile.  If you sure, type this command again."
                )
        );
        sure = true;

    }
}
