package com.cogworks.flashpoint.registry;

import com.cogworks.flashpoint.Flashpoint;

import com.cogworks.flashpoint.entities.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Flashpoint.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<GasolineBlobProjectile>> GASOLINE_BLOB_PROJECTILE =
            ENTITY_TYPES.register("gasoline_blob_projectile", id -> EntityType.Builder.of(GasolineBlobProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(id.getPath()));
    public static final DeferredHolder<EntityType<?>, EntityType<GasolineProjectile>> GASOLINE_PROJECTILE =
            ENTITY_TYPES.register("gasoline_projectile", id -> EntityType.Builder.of(GasolineProjectile::new, MobCategory.MISC)
                    .sized(0.3f, 0.3f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(id.getPath()));
}