package com.cogworks.unorthodoxweapons.client.render;

import com.cogworks.unorthodoxweapons.entities.BlockHoleEntity;
import com.cogworks.unorthodoxweapons.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class BlockHoleRenderer extends EntityRenderer<BlockHoleEntity> {

    private final ItemRenderer itemRenderer;

    public BlockHoleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BlockHoleEntity entity, float entityYRot, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        float yRot = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

        poseStack.mulPose(Axis.YP.rotationDegrees(yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(xRot));
        //poseStack.scale(3,3,3);

        ItemStack modelStack = new ItemStack(ModItems.BLOCK_HOLE_MODEL.get());
        
        this.itemRenderer.renderStatic(
            modelStack, 
            ItemDisplayContext.HEAD,
            15,
            NO_OVERLAY,
            poseStack, 
            buffer, 
            entity.level(), 
            entity.getId()
        );

        poseStack.popPose();
        super.render(entity, entityYRot, partialTicks, poseStack, buffer, packedLight);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlockHoleEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
