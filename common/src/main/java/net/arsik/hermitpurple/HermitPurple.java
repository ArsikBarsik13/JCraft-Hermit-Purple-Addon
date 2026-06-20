package net.arsik.hermitpurple;

import dev.architectury.registry.registries.DeferredRegister;
import net.arsik.hermitpurple.common.register.EntityTypeRegistry;
import net.arsik.hermitpurple.common.register.HPSoundRegistry;
import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class HermitPurple {
    public static final String MOD_ID = "hermit_purple";
    public static final String BASE_CONTROLLER = "base_controller";

    public static final DeferredRegister<SoundEvent> HPSOUNDS = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);

    public static void init() {
        EntityTypeRegistry.REGISTRY.register();
        StandTypeRegistry.REGISTRY.register();

        EntityTypeRegistry.registerAttributes();

        HPSoundRegistry.init();
        HPSOUNDS.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
