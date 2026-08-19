package com.cogworks.unorthodoxweapons.network;

import com.cogworks.unorthodoxweapons.UnorthodoxWeapons;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record AdminActionPayload(boolean primary, int targetEntityId, Optional<BlockPos> targetBlock) implements CustomPacketPayload {

    public static final Type<AdminActionPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(UnorthodoxWeapons.MODID, "admin_action"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AdminActionPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, AdminActionPayload::primary,
                    ByteBufCodecs.VAR_INT, AdminActionPayload::targetEntityId,
                    ByteBufCodecs.optional(BlockPos.STREAM_CODEC), AdminActionPayload::targetBlock,
                    AdminActionPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}