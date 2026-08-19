package com.cogworks.gaslib.entities;

import com.cogworks.gaslib.registry.ModEntities;
import com.cogworks.gaslib.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GasolineBlobProjectile extends ThrowableItemProjectile {

    public GasolineBlobProjectile(EntityType<? extends GasolineBlobProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.GASOLINE_MODEL.get();
    }

    @Override
    public void tick() {
        super.tick();
        Level level = this.level();
        if (level.isClientSide) return;
        if (this.tickCount % 5 != 0) return;
        EntityType<?> type = ModEntities.GASOLINE_PROJECTILE.get();
        if (type == null) return;
        Entity created = type.create(level);
        if (created == null) return;
        created.setPos(this.getX(), this.getY(), this.getZ());
        Vec3 motion = this.getDeltaMovement();
        created.setDeltaMovement(motion);
        if (created instanceof GasolineProjectile) {
            ((GasolineProjectile) created).setOwner(this.getOwner());
        }
        level.addFreshEntity(created);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        enum faceHit {
            WALL
        }
    }
}
