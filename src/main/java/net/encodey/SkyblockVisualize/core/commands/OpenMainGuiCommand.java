package net.encodey.SkyblockVisualize.core.commands;

import net.encodey.SkyblockVisualize.SkyblockVisualize;
import net.encodey.SkyblockVisualize.core.gui.NewMainGui;

public class OpenMainGuiCommand extends CommandManager {

    @Override
    String commandName() {
        return "skyblockvisualize";
    }

    @Override
    String commandAlias() {
        return "sv";
    }

    @Override
    public void processCommand() {
        SkyblockVisualize.INSTANCE.currentScreen = new NewMainGui();
    }
}
