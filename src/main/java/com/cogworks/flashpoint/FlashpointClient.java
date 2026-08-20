package com.cogworks.flashpoint;

import com.cogworks.flashpoint.client.render.*;
import com.cogworks.flashpoint.registry.ModBlocks;
import com.cogworks.flashpoint.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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

@Mod(value = Flashpoint.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Flashpoint.MODID, value = Dist.CLIENT)
public class FlashpointClient {
    public FlashpointClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::registerRenderers);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GASOLINE_BLOB_PROJECTILE.get(), GasolineBlobProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.GASOLINE_PROJECTILE.get(), GasolineProjectileRenderer::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(ModBlocks.GASOLINE_SPREAD.get(), RenderType.cutout()));
        Flashpoint.LOGGER.info("HELLO FROM CLIENT SETUP");
        Flashpoint.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}