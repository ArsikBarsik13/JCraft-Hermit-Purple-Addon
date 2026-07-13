package net.arsik.jcraftpolished.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.arsik.hermitpurple.HermitPurple;

@Mod(HermitPurple.MOD_ID)
public final class HermitPurpleForge {
    public HermitPurpleForge(FMLJavaModLoadingContext ctx) {
        EventBuses.registerModEventBus(HermitPurple.MOD_ID, ctx.getModEventBus());

        HermitPurple.init();
    }
}
