package com.cogworks.flashpoint.client.render;

import com.cogworks.flashpoint.entities.GasolineProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.jetbrains.annotations.NotNull;

public class GasolineProjectileRenderer extends ThrownItemRenderer<GasolineProjectile> {

    public GasolineProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull GasolineProjectile entity, float entityYRot, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.tickCount > 0 && isProjectileFrozen(entity)) {
            return;
        }

        super.render(entity, entityYRot, partialTicks, poseStack, buffer, packedLight);
    }

    private boolean isProjectileFrozen(GasolineProjectile entity) {
        return entity.getDeltaMovement().lengthSqr() < 0.001D;
    }
}