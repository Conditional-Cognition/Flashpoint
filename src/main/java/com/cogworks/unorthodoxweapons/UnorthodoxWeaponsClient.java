package com.cogworks.unorthodoxweapons;
/*
import com.cogworks.unorthodoxweapons.registry.ModEntities;

import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownTrident;*/
import com.cogworks.unorthodoxweapons.client.render.BlockHoleProjectileRenderer;
import com.cogworks.unorthodoxweapons.client.render.BlockHoleRenderer;
import com.cogworks.unorthodoxweapons.client.render.CustomTridentRenderer;
import com.cogworks.unorthodoxweapons.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//import org.jetbrains.annotations.NotNull;

@Mod(value = UnorthodoxWeapons.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = UnorthodoxWeapons.MODID, value = Dist.CLIENT)
public class UnorthodoxWeaponsClient {
    public UnorthodoxWeaponsClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::registerRenderers);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GASOLINE_BLOB.get(), BlockHoleProjectileRenderer::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        UnorthodoxWeapons.LOGGER.info("HELLO FROM CLIENT SETUP");
        UnorthodoxWeapons.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}