package com.cogworks.flashpoint.items;

import com.cogworks.flashpoint.entities.GasolineBlobProjectile;
import com.cogworks.flashpoint.registry.ModEntities;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache; // <--- import
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FirestarterItem extends Item implements GeoItem {

    // Animatable cache (necessary for AzureLib/Geckolib)
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public FirestarterItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level world, @NotNull LivingEntity user, @NotNull ItemStack stack, int count) {
        if (world.isClientSide) return;
        int used = this.getUseDuration(stack) - count;
        int fireInterval = 3;
        if (used % fireInterval == 0) {
            spawnBlob(world, (Player) user);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    public int getUseDuration(@SuppressWarnings("unused") ItemStack stack) {
        return 72000;
    }

    @Override
    public void createRenderer(Consumer<RenderProvider> consumer) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void shootServer(Level world, Player user) {
        if (world.isClientSide) return;

        int cooldownTicks = 20;
        user.getCooldowns().addCooldown(this, cooldownTicks);

        spawnBlob(world, user);
    }

    private void spawnBlob(Level world, Player user) {
        Vec3 look = user.getLookAngle();
        Vec3 eye = user.getEyePosition(1.0F);
        GasolineBlobProjectile blob = new GasolineBlobProjectile(ModEntities.GASOLINE_BLOB_PROJECTILE.get(), world);
        blob.setPos(eye.x + look.x * 0.5, eye.y + look.y * 0.5, eye.z + look.z * 0.5);
        float speed = 1.2f;
        blob.shoot(look.x, look.y, look.z, speed, 0.0f);
        blob.setOwner(user);
        world.addFreshEntity(blob);
    }
}