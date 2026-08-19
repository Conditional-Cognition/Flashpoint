package com.cogworks.unorthodoxweapons.client.render;

import com.cogworks.unorthodoxweapons.entities.BlockHoleProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.jetbrains.annotations.NotNull;

public class BlockHoleProjectileRenderer extends ThrownItemRenderer<BlockHoleProjectile> {

    public BlockHoleProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull BlockHoleProjectile entity, float entityYRot, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.tickCount > 0 && isProjectileFrozen(entity)) {
            return;
        }

        super.render(entity, entityYRot, partialTicks, poseStack, buffer, packedLight);
    }

    private boolean isProjectileFrozen(BlockHoleProjectile entity) {
        return entity.getDeltaMovement().lengthSqr() < 0.001D;
    }
}