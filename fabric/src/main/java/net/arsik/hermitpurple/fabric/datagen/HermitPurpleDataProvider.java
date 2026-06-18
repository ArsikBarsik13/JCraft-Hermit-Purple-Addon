package net.arsik.hermitpurple.fabric.datagen;

import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.arsik.hermitpurple.common.stand.HermitPurpleEntity;
import net.arna.jcraft.api.datagen.JCraftStandDataProvider;
import net.arna.jcraft.api.stand.StandData;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class HermitPurpleDataProvider extends JCraftStandDataProvider {
    public HermitPurpleDataProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, StandData> provider) {
        provider.accept(StandTypeRegistry.HERMIT_PURPLE.getId(), HermitPurpleEntity.DATA);
    }
}
