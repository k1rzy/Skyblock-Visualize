package net.encodey.SkyblockVisualize;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;

import java.util.*;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public class TweakClass implements IFMLLoadingPlugin {

    public static HashMap<String, byte[]> classes;

    public TweakClass() {
        MixinBootstrap.init();
    }

    public String[] getASMTransformerClass() {
        return new String[] { Tills.class.getName() };
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(final Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }

    static {
        classes = new HashMap<>();
    }
}
