package net.encodey.SkyblockVisualize.core.gui;

import net.encodey.SkyblockVisualize.player.ConfigStats;

public interface GuiVisualize {

    default double PLAYER_PURSE() {
        return ConfigStats.playerPurse;
    }

    default double PLAYER_HEALTH() {
        return ConfigStats.health;
    }

    default double PLAYER_MAX_HEALTH() {
        return ConfigStats.playerMaxHealth;
    }

    default double PLAYER_FOOD() {
        return ConfigStats.playerFood;
    }

    default String SELECTED_ITEM() {
        return ConfigStats.selectedItem;
    }

    default int BLOCK_COUNT() {
        return ConfigStats.blockCount;
    }

    default int MATERIAL_COUNT() {
        return ConfigStats.materialCount;
    }

    default int PLAYER_DEFENSE() {
        return ConfigStats.playerDefense;
    }
}
