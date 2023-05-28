package dev.quarris.commiecraft;

import net.minecraft.resources.ResourceLocation;

public class ModRef {
    public static final String ID = "commiecraft";

    public static ResourceLocation res(String name) {
        return new ResourceLocation(name);
    }
}
