package net.arsik.hermitpurple;

import net.arsik.hermitpurple.common.register.EntityTypeRegistry;
import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.minecraft.resources.ResourceLocation;

public final class HermitPurple {
    public static final String MOD_ID = "hermit_purple";
    public static final String BASE_CONTROLLER = "base_controller";

    public static void init() {
        EntityTypeRegistry.REGISTRY.register();
        StandTypeRegistry.REGISTRY.register();

        EntityTypeRegistry.registerAttributes();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
