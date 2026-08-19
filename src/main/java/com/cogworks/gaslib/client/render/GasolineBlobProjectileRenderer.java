package com.cogworks.gaslib.client.render;

import com.cogworks.gaslib.entities.GasolineBlobProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.jetbrains.annotations.NotNull;

public class GasolineBlobProjectileRenderer extends ThrownItemRenderer<GasolineBlobProjectile> {

    public GasolineBlobProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull GasolineBlobProjectile entity, float entityYRot, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.tickCount > 0 && isProjectileFrozen(entity)) {
            return;
        }

        super.render(entity, entityYRot, partialTicks, poseStack, buffer, packedLight);
    }

    private boolean isProjectileFrozen(GasolineBlobProjectile entity) {
        return entity.getDeltaMovement().lengthSqr() < 0.001D;
    }
}