package net.encodey.SkyblockVisualize.player;

import net.encodey.SkyblockVisualize.packages.Option;
import net.encodey.SkyblockVisualize.packages.Parameter;

public class StatSaver {

    protected Parameter<String> param = new Option<String, String>().optionOfType("");

    public StatSaver() {
        GetterStats.saveStat("player_mental_stats", "food", ConfigStats.playerFood);
        GetterStats.saveStat("player_mental_stats", "max_food", ConfigStats.playerMaxFood);
        GetterStats.saveStat("player_skills", "sprinting", ConfigStats.sprintLevel);
        GetterStats.saveStat("skills_exp", "sprint_skill", LevelProfiler.sprintingLevelExp);
    }
}
