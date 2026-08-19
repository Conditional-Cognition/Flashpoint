package com.cogworks.unorthodoxweapons.client;

import com.cogworks.unorthodoxweapons.registry.ModItems;
import com.cogworks.unorthodoxweapons.network.AdminActionPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

@EventBusSubscriber(modid = "unorthodoxweapons", bus = EventBusSubscriber.Bus.GAME, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class AdminToolClientHandler {

    private static boolean isHoldingAdminTool() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.player.getMainHandItem().is(ModItems.ADMIN_TOOL.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (isHoldingAdminTool() && KeyMappings.OPEN_ADMIN_RADIAL.consumeClick() && mc.screen == null) {
            mc.setScreen(new com.cogworks.unorthodoxweapons.client.gui.AdminRadialScreen());
        }
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseButton.Pre event) {
        if (!isHoldingAdminTool()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null || event.getAction() != org.lwjgl.glfw.GLFW.GLFW_PRESS) return;

        boolean primary = event.getButton() == 0;
        boolean secondary = event.getButton() == 1;
        if (!primary && !secondary) return;

        HitResult hit = mc.hitResult;
        if (hit == null) return;

        int entityId = -1;
        Optional<net.minecraft.core.BlockPos> blockPos = Optional.empty();

        if (hit instanceof EntityHitResult eh) {
            entityId = eh.getEntity().getId();
        } else if (hit instanceof BlockHitResult bh && bh.getType() != HitResult.Type.MISS) {
            blockPos = Optional.of(bh.getBlockPos());
        } else {
            return;
        }

        PacketDistributor.sendToServer(new AdminActionPayload(primary, entityId, blockPos));
        event.setCanceled(true);
    }
}