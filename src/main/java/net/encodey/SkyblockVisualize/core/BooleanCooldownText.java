package net.encodey.SkyblockVisualize.core;

import lombok.Getter;
import lombok.Setter;

public class BooleanCooldownText {
    static @Getter
    private BooleanCooldownText cooldown = new BooleanCooldownText();

    @Getter
    @Setter
    private boolean _newProfileCommandUsed;
}
