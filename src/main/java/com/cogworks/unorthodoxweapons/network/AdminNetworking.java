package com.cogworks.unorthodoxweapons.network;

import com.cogworks.unorthodoxweapons.items.admin.AdminModeAttachment;
import com.cogworks.unorthodoxweapons.registry.ModParticles;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AdminNetworking {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(AdminModePayload.TYPE, AdminModePayload.STREAM_CODEC,
                (payload, context) -> context.player().setData(AdminModeAttachment.ADMIN_GUN_MODE, payload.mode()));

        registrar.playToServer(AdminActionPayload.TYPE, AdminActionPayload.STREAM_CODEC,
                (payload, context) -> {
                    if (context.player() instanceof ServerPlayer sp) {
                        handleAction(sp, payload);
                    }
                });
    }

    private static void spawnMuzzleFlash(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        Vec3 look = player.getLookAngle();

        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();
        boolean isLeftHanded = player.getMainArm() == net.minecraft.world.entity.HumanoidArm.LEFT;
        right = right.scale(isLeftHanded ? -0.35 : 0.35);

        Vec3 muzzle = player.getEyePosition()
                .add(look.scale(1.2))
                .add(right)
                .add(0, -0.15, 0);

        level.sendParticles(ModParticles.ADMIN_GUN_SHOOT_FLASH.get(),
                muzzle.x, muzzle.y, muzzle.z,
                1, 0, 0, 0, 0);
    }

    private static void handleAction(ServerPlayer player, AdminActionPayload payload) {
        var mode = player.getData(AdminModeAttachment.ADMIN_GUN_MODE);
        ServerLevel level = player.serverLevel();

        spawnMuzzleFlash(player);

        switch (mode) {
            case KILL_KICK -> {
                Entity target = level.getEntity(payload.targetEntityId());
                if (target == null) return;

                Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
                level.sendParticles(ModParticles.ADMIN_GUN_SHOOT_FLASH.get(),
                        targetPos.x, targetPos.y, targetPos.z, 1, 0, 0, 0, 0);

                if (payload.primary()) {
                    target.kill();
                } else if (target instanceof ServerPlayer targetPlayer) {
                    targetPlayer.connection.disconnect(Component.literal("Kicked by " + player.getGameProfile().getName()));
                }
            }
            case BLOCK_GRAB -> payload.targetBlock().ifPresent(pos -> {
                var state = level.getBlockState(pos);
                if (state.isAir()) return;

                ItemStack drop = new ItemStack(state.getBlock());
                if (payload.primary()) {
                    level.removeBlock(pos, false);
                } else {
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be != null) {
                        be.saveToItem(drop, level.registryAccess());
                    }
                    level.removeBlockEntity(pos);
                    level.removeBlock(pos, false);
                }

                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }

                level.sendParticles(ModParticles.ADMIN_GUN_SHOOT_FLASH.get(),
                        Vec3.atCenterOf(pos).x, Vec3.atCenterOf(pos).y, Vec3.atCenterOf(pos).z,
                        1, 0, 0, 0, 0);
            });
        }
    }
}