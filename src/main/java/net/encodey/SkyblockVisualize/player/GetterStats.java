package net.encodey.SkyblockVisualize.player;

import net.encodey.SkyblockVisualize.SkyblockVisualize;
import net.encodey.SkyblockVisualize.core.commands.NewProfileCommand;
import net.encodey.SkyblockVisualize.exceptions.CannotCreateConfigException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import org.lwjgl.Sys;
import scala.Int;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class GetterStats {
    public static Configuration config;
    private final static String file = "config/svProfile1.cfg";

    public static void init() {
        config = new Configuration(new File(file));
        try {
            config.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static int getIntegerStat(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return 0;
    }

    public static double getDoubleStat(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return 0D;
    }

    public static String getStringStat(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return "";
    }

    public static boolean getBooleanStat(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, false).getBoolean();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return true;
    }

    public static void writeIntegerStat(String category, String key, int value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeDoubleStat(String category, String key, double value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeStringStat(String category, String key, String value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeBooleanStat(String category, String key, boolean value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            boolean set = config.get(category, key, value).getBoolean();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static boolean hasKey(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (!config.hasCategory(category)) return false;
            return config.getCategory(category).containsKey(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return false;
    }

    public static void deleteCategoryStat(String category) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.hasCategory(category)) {
                config.removeCategory(new ConfigCategory(category));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static int initIntegerStat(String category, String key, int defaultValue) {
        if (!hasKey(category, key)) {
            writeIntegerStat(category, key, defaultValue);
            return defaultValue;
        } else {
            return getIntegerStat(category, key);
        }
    }

    public static double initDoubleStat(String category, String key, double defaultValue) {
        if (!hasKey(category, key)) {
            writeDoubleStat(category, key, defaultValue);
            return defaultValue;
        } else {
            return getDoubleStat(category, key);
        }
    }

    public static String initStringStat(String category, String key, String defaultValue) {
        if (!hasKey(category, key)) {
            writeStringStat(category, key, defaultValue);
            return defaultValue;
        } else {
            return getStringStat(category, key);
        }
    }

    public static boolean initBooleanStat(String category, String key, boolean defaultValue) {
        if (!hasKey(category, key)) {
            writeBooleanStat(category, key, defaultValue);
            return defaultValue;
        } else {
            return getBooleanStat(category, key);
        }
    }


    public static<T> void saveStat(final String category, final String key, final T value) {
        if (value == null) return;
        if (value instanceof Integer) {
            writeIntegerStat(category, key, (Integer)value);
        }
        else if (value instanceof Double) {
            writeDoubleStat(category, key, (Double)value);
        }
        else if (value instanceof String) {
            writeStringStat(category, key, (String)value);
        }
        else if (value instanceof Boolean) {
            writeBooleanStat(category, key, (Boolean)value);
        }
    }

    public static void reload() {
        NewProfileCommand.createdProfile = initBooleanStat("main", "created_profile", false);

        ConfigStats.blockCount = initIntegerStat("inventory", "block_count", 4);
        ConfigStats.materialCount = initIntegerStat("inventory", "item_count", 0);
        ConfigStats.playerDefense = initIntegerStat("player_armour", "defense", 5);
        ConfigStats.sprintLevel = initIntegerStat("player_skills", "sprinting", 0);
        LevelProfiler.sprintingLevelExp = initIntegerStat("skills_exp", "sprint_skill", 0);

        ConfigStats.selectedItem = initStringStat("inventory", "selected_item", "None");

        ConfigStats.playerPurse = initDoubleStat("bank", "purse", 10.0);
        ConfigStats.playerMaxHealth = initDoubleStat("player_mental_stats", "max_health", 20.0);
        ConfigStats.playerFood = initDoubleStat("player_mental_stats", "food", 20.0);
        ConfigStats.playerMaxFood = initDoubleStat("player_mental_stats", "max_food", 20.0);
        ConfigStats.health = initDoubleStat("player_mental_stats", "health", 20.0);
    }
}
