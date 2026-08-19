package com.cogworks.unorthodoxweapons.network;

import com.cogworks.unorthodoxweapons.UnorthodoxWeapons;
import com.cogworks.unorthodoxweapons.items.admin.AdminGunMode;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record AdminModePayload(AdminGunMode mode) implements CustomPacketPayload {

    public static final Type<AdminModePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(UnorthodoxWeapons.MODID, "admin_mode"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AdminModePayload> STREAM_CODEC =
            StreamCodec.composite(
                    StreamCodec.of(
                            FriendlyByteBuf::writeEnum,
                            buf -> buf.readEnum(AdminGunMode.class)
                    ), AdminModePayload::mode,
                    AdminModePayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}