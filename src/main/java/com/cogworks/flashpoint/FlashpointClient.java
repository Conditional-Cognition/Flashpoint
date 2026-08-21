package com.cogworks.flashpoint;

import com.cogworks.flashpoint.client.render.*;
import com.cogworks.flashpoint.registry.ModBlocks;
import com.cogworks.flashpoint.registry.ModEntities;
import com.cogworks.flashpoint.registry.ModItems;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
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
import net.neoforged.neoforge.registries.DeferredHolder;

@Mod(value = Flashpoint.MODID, dist = Dist.CLIENT) @SuppressWarnings("removal")
@EventBusSubscriber(modid = Flashpoint.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD) // Added explicit MOD bus targeting
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
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.GASOLINE_SPREAD.get(), RenderType.cutout());

            Item rawItem = ModItems.ITEMS.getEntries().stream()
                    .filter(holder -> holder.getId().getPath().equals("firestarter"))
                    .map(DeferredHolder::get)
                    .map(Item.class::cast)
                    .findFirst()
                    .orElse(Items.AIR);

            if (rawItem != Items.AIR) {
                AzItemRendererRegistry.register(rawItem, FirestarterRenderer::new);
            }

        });

        Flashpoint.LOGGER.info("HELLO FROM CLIENT SETUP WITH AZURELIB WORKING!");
        Flashpoint.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
