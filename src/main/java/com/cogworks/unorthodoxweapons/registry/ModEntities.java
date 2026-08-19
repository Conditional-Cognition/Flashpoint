package com.cogworks.unorthodoxweapons.registry;

import com.cogworks.unorthodoxweapons.UnorthodoxWeapons;

import com.cogworks.unorthodoxweapons.entities.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, UnorthodoxWeapons.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BlockHoleProjectile>> GASOLINE_BLOB_PROJECTILE =
            ENTITY_TYPES.register("gasoline_blob_projectile", id -> EntityType.Builder.of(BlockHoleProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(id.getPath()));
}