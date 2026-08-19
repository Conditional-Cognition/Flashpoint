package com.cogworks.gaslib.entities;

import com.cogworks.gaslib.blocks.GasolineSpread;
import com.cogworks.gaslib.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class GasolineProjectile extends ThrowableItemProjectile {
    public GasolineProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.GASOLINE_MODEL.get();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hit) {
        super.onHitBlock(hit);
        if (this.level().isClientSide) return;
        BlockPos hitPos = hit.getBlockPos();
        Direction face = hit.getDirection();
        GasolineSpread.placeWithFallback(this.level(), hitPos, face);
        this.discard();
    }
}
