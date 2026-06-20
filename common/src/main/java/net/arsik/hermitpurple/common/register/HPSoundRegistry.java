package net.arsik.hermitpurple.common.register;

import dev.architectury.registry.registries.RegistrySupplier;
import net.arsik.hermitpurple.HermitPurple;
import net.minecraft.sounds.SoundEvent;

import static net.arsik.hermitpurple.HermitPurple.HPSOUNDS;

public interface HPSoundRegistry {

    static RegistrySupplier<SoundEvent> register(String name) {
        var event = SoundEvent.createVariableRangeEvent(HermitPurple.id(name));
        return HPSOUNDS.register(event.getLocation().getPath(), () -> event);
    }

    RegistrySupplier<SoundEvent> HP_SUMMON = register("hpsummon");

    static void init() {
        // intentionally left empty
    }
}