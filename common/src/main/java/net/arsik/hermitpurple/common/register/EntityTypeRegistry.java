package net.arsik.hermitpurple.common.register;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.arsik.hermitpurple.HermitPurple;
import net.arsik.hermitpurple.common.stand.HermitPurpleEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public interface EntityTypeRegistry {
    DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(HermitPurple.MOD_ID, Registries.ENTITY_TYPE);

    RegistrySupplier<EntityType<HermitPurpleEntity>> HERMIT_PURPLE = REGISTRY.register("hermit_purple", () ->
            EntityType.Builder.of((EntityType.EntityFactory<HermitPurpleEntity>)
                            (t, level) -> new HermitPurpleEntity(level),
                            MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("hermit_purple"));

    static void registerAttributes() {
        EntityAttributeRegistry.register(HERMIT_PURPLE, HermitPurpleEntity::createMobAttributes);
    }
}
