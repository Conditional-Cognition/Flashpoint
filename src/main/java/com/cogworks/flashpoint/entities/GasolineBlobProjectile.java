package com.cogworks.flashpoint.entities;

import com.cogworks.flashpoint.blocks.GasolineSpread;
import com.cogworks.flashpoint.registry.ModEntities;
import com.cogworks.flashpoint.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
    protected void onHitBlock(@NotNull BlockHitResult hit) {
        super.onHitBlock(hit);
        if (this.level().isClientSide) return;
        BlockPos hitPos = hit.getBlockPos();
        Direction face = hit.getDirection();
        BlockPos origin = hitPos.relative(face); // place in the neighboring space (the space on the face)
        Direction placeFace = face.getOpposite(); // the face on the placed block that faces the original block

        BlockPos[] targets;
        if (face.getAxis() == Direction.Axis.Y) {
            targets = new BlockPos[] {
                    origin,
                    origin.offset(-1, 0, -1),
                    origin.offset(1, 0, -1),
                    origin.offset(-1, 0, 1),
                    origin.offset(1, 0, 1)
            };
        } else if (face.getAxis() == Direction.Axis.Z) {
            targets = new BlockPos[] {
                    origin,
                    origin.offset(-1, -1, 0),
                    origin.offset(1, -1, 0),
                    origin.offset(-1, 1, 0),
                    origin.offset(1, 1, 0)
            };
        } else {
            targets = new BlockPos[] {
                    origin,
                    origin.offset(0, -1, -1),
                    origin.offset(0, 1, -1),
                    origin.offset(0, -1, 1),
                    origin.offset(0, 1, 1)
            };
        }

        for (BlockPos p : targets) GasolineSpread.placeWithFallback(this.level(), p, placeFace);
        this.discard();
    }
}
