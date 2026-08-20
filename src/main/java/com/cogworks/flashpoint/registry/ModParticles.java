package com.cogworks.flashpoint.registry;

import com.cogworks.flashpoint.Flashpoint;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, Flashpoint.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GASOLINE_DRIP =
            PARTICLE_TYPES.register("gasoline_drip", () -> new SimpleParticleType(false));
}