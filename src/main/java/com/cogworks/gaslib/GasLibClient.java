package com.cogworks.gaslib;
/*
import com.cogworks.unorthodoxweapons.registry.ModEntities;

import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownTrident;*/
import com.cogworks.gaslib.client.render.GasolineBlobProjectileRenderer;
import com.cogworks.gaslib.registry.ModBlocks;
import com.cogworks.gaslib.registry.ModEntities;
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
//import org.jetbrains.annotations.NotNull;

@Mod(value = GasLib.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = GasLib.MODID, value = Dist.CLIENT)
public class GasLibClient {
    public GasLibClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::registerRenderers);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GASOLINE_BLOB_PROJECTILE.get(), GasolineBlobProjectileRenderer::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(ModBlocks.GASOLINE_SPREAD.get(), RenderType.cutout()));
        GasLib.LOGGER.info("HELLO FROM CLIENT SETUP");
        GasLib.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}