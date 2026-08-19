package com.cogworks.unorthodoxweapons.entities;

import com.cogworks.unorthodoxweapons.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlockHoleProjectile extends ThrowableItemProjectile {

    public BlockHoleProjectile(EntityType<? extends BlockHoleProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.GASOLINE_MODEL.get();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {

    }
}
