package net.encodey.SkyblockVisualize.player;

public class StatSaver {

    public StatSaver() {
        GetterStats.saveStat("player_mental_stats", "food", ConfigStats.playerFood);
        GetterStats.saveStat("player_mental_stats", "max_food", ConfigStats.playerMaxFood);
        GetterStats.saveStat("player_skills", "sprinting", ConfigStats.sprintLevel);
        GetterStats.saveStat("skills_exp", "sprint_skill", LevelProfiler.sprintingLevelExp);
    }
}
