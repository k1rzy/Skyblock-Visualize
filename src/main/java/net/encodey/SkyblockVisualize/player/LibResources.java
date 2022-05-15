package net.encodey.SkyblockVisualize.player;

import net.minecraft.util.ResourceLocation;

public abstract class LibResources {

    private static final String DOMAIN = "svtextures";

    public static final ResourceLocation CRAFTING_TABLE_2D;
    public static final ResourceLocation SPRINTING_SKILL;

    static {
        CRAFTING_TABLE_2D = new ResourceLocation(DOMAIN, "textures/crafting_table.png");
        SPRINTING_SKILL = new ResourceLocation(DOMAIN, "textures/skills/sprinting.png");
    }
}
