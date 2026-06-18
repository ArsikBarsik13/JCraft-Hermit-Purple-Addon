package net.arsik.hermitpurple.client.renderer;

import net.arna.jcraft.client.renderer.entity.stands.StandEntityRenderer;
import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.arsik.hermitpurple.common.stand.HermitPurpleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class HermitPurpleRenderer extends StandEntityRenderer<HermitPurpleEntity> {
    public HermitPurpleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, StandTypeRegistry.HERMIT_PURPLE.get());
    }
}