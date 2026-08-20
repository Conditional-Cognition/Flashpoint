package com.cogworks.flashpoint.client.particle;

import com.cogworks.flashpoint.Flashpoint;
import com.cogworks.flashpoint.registry.ModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = Flashpoint.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientParticleHandler {

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.GASOLINE_DRIP.get(), GasolineDripParticle.Provider::new);
    }
}