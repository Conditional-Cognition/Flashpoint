package com.cogworks.gaslib.client;

import com.cogworks.gaslib.GasLib;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = GasLib.MODID, bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class KeyMappings {

    public static final KeyMapping OPEN_ADMIN_RADIAL = new KeyMapping(
            "key."+GasLib.MODID+".admin_radial",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_R,
            "key.categories."+GasLib.MODID
    );

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_ADMIN_RADIAL);
    }
}