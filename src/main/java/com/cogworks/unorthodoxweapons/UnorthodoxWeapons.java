package com.cogworks.unorthodoxweapons;

import com.cogworks.unorthodoxweapons.items.admin.AdminModeAttachment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.cogworks.unorthodoxweapons.registry.*;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(UnorthodoxWeapons.MODID)
public class UnorthodoxWeapons {
    public static final String MODID = "gasoline_library";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UnorthodoxWeapons(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        AdminModeAttachment.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}