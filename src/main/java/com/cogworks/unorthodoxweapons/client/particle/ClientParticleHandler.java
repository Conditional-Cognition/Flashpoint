package com.cogworks.unorthodoxweapons.client.particle;

import com.cogworks.unorthodoxweapons.registry.ModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = "unorthodoxweapons", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientParticleHandler {

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ADMIN_GUN_SHOOT_FLASH.get(), AdminGunShootFlashParticle.Provider::new);
    }
}