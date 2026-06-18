package net.arsik.hermitpurple.fabric.datagen.movesets;

import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.arsik.hermitpurple.common.stand.HermitPurpleEntity;
import net.arna.jcraft.api.attack.MoveMap;
import net.arna.jcraft.api.attack.MoveSet;
import net.arna.jcraft.api.datagen.JCraftMoveSetProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.util.function.Consumer;

public class HermitPurpleMoveSetProvider extends JCraftMoveSetProvider<HermitPurpleEntity, HermitPurpleEntity.State> {
    public HermitPurpleMoveSetProvider(FabricDataOutput dataOutput) {
        super(dataOutput, MoveMap.Entry.codecFor(HermitPurpleEntity.State.class), StandTypeRegistry.HERMIT_PURPLE.getId());
    }

    @Override
    protected void configureMoveSets(Consumer<MoveSet<HermitPurpleEntity, HermitPurpleEntity.State>> provider) {
        provider.accept(HermitPurpleEntity.MOVE_SET);
    }
}
