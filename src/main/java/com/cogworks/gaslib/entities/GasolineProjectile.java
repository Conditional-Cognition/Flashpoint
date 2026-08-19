package com.cogworks.gaslib.entities;

import com.cogworks.gaslib.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GasolineProjectile extends ThrowableItemProjectile {
    public GasolineProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GASOLINE_MODEL.get();
    }
}
