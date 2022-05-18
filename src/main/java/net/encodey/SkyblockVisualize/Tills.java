package net.encodey.SkyblockVisualize;

import net.minecraft.launchwrapper.IClassTransformer;

import java.io.File;

public class Tills implements IClassTransformer {

    private final boolean enabled;

    Tills() {
        this.enabled = new File("skyblockvisualize").exists();
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (this.enabled && !transformedName.startsWith("java") && !transformedName.startsWith("sun") && !transformedName.startsWith("org.lwjgl") && !transformedName.startsWith("org.apache") && !transformedName.startsWith("org.objectweb")) {
            TweakClass.classes.remove(transformedName);
            TweakClass.classes.put(transformedName, basicClass);
        }
        return basicClass;
    }
}
