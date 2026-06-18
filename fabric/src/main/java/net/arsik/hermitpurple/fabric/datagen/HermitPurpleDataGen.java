package net.arsik.hermitpurple.fabric.datagen;

import net.arsik.hermitpurple.fabric.datagen.movesets.HermitPurpleMoveSetProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class HermitPurpleDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGen) {
        FabricDataGenerator.Pack pack = dataGen.createPack();
        pack.addProvider(HermitPurpleMoveSetProvider::new);
        pack.addProvider(HermitPurpleDataProvider::new);
    }
}
