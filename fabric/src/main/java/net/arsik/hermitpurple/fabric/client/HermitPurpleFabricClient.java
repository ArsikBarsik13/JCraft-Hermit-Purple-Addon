package net.arsik.hermitpurple.fabric.client;

import net.arsik.hermitpurple.client.register.EntityRendererRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public final class HermitPurpleFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        net.arsik.hermitpurple.client.HermitPurpleClient.init();

        // Register entity renderers
        EntityRendererRegister.register(new EntityRendererRegister.EntityRendererRegistrar() {
            @Override
            public <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> rendererProvider) {
                EntityRendererRegistry.register(type.get(), rendererProvider);
            }
        });
    }
}
