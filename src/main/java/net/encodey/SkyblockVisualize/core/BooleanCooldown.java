package net.encodey.SkyblockVisualize.core;

import lombok.Getter;
import lombok.Setter;

public class BooleanCooldown {
    static @Getter
    private BooleanCooldown cooldown = new BooleanCooldown();

    @Getter
    @Setter
    private boolean _newProfileCommandUsed;

    @Getter
    @Setter
    private boolean _soundLocationUsed;
}
