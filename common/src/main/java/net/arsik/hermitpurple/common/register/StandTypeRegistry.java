package net.arsik.hermitpurple.common.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.arna.jcraft.api.JRegistries;
import net.arna.jcraft.api.stand.StandType;
import net.arsik.hermitpurple.HermitPurple;

public interface StandTypeRegistry {
    DeferredRegister<StandType> REGISTRY = DeferredRegister.create(HermitPurple.MOD_ID, JRegistries.STAND_TYPE_REGISTRY_KEY);

    RegistrySupplier<StandType> HERMIT_PURPLE = REGISTRY.register(HermitPurple.id("hermit_purple"), () ->
            StandType.of(HermitPurple.id("hermit_purple"), EntityTypeRegistry.HERMIT_PURPLE));
}
