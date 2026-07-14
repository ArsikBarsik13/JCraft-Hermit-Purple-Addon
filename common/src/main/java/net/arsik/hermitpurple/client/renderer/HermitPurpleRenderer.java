package net.arsik.hermitpurple.client.renderer;

import lombok.NonNull;
import mod.azure.azurelib.model.AzBakedModel;
import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.armor.bone.AzArmorBoneProvider;
import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.arsik.hermitpurple.common.stand.HermitPurpleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HermitPurpleRenderer extends AbstractExoskeletonRenderer<HermitPurpleEntity> {

    @Override
    public net.minecraft.resources.@NotNull ResourceLocation getTextureLocation(@NotNull HermitPurpleEntity entity) {
        return getTextureLocation((net.arna.jcraft.api.stand.StandEntity<?, ?>) entity);
    }

    private static final AzArmorBoneProvider BONE_PROVIDER = new AzArmorBoneProvider() {
        @Override public AzBone getHeadBone(AzBakedModel m)     { return m.getBoneOrNull("head");     }
        @Override public AzBone getBodyBone(AzBakedModel m)     { return m.getBoneOrNull("body");     }
        @Override public AzBone getRightArmBone(AzBakedModel m) { return m.getBoneOrNull("armRight"); }
        @Override public AzBone getLeftArmBone(AzBakedModel m)  { return m.getBoneOrNull("armLeft");  }
        @Override public AzBone getRightLegBone(AzBakedModel m) { return m.getBoneOrNull("legRight"); }
        @Override public AzBone getLeftLegBone(AzBakedModel m)  { return m.getBoneOrNull("legLeft");  }
        @Override public AzBone getRightBootBone(AzBakedModel m){ return null; }
        @Override public AzBone getLeftBootBone(AzBakedModel m) { return null; }
        @Override public AzBone getWaistBone(AzBakedModel m)    { return null; }
    };

    public HermitPurpleRenderer(final @NonNull EntityRendererProvider.Context context) {
        super(context, b -> b.setRenderType(renderType(RenderType::entityTranslucentCull)),
                StandTypeRegistry.HERMIT_PURPLE.get(), false, false, 0f, 0f, 1f, BONE_PROVIDER);
    }

}

/*import net.arna.jcraft.client.renderer.entity.stands.StandEntityRenderer;
import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import net.arsik.hermitpurple.common.stand.HermitPurpleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class HermitPurpleRenderer extends StandEntityRenderer<HermitPurpleEntity> {
    public HermitPurpleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, StandTypeRegistry.HERMIT_PURPLE.get());
    }
}*/
