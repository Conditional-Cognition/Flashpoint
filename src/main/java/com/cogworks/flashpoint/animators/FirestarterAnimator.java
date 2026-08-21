package com.cogworks.flashpoint.animators;

import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzItemAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FirestarterAnimator extends AzItemAnimator {
    private static final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(
            "flashpoint",
            "animations/item/firestarter.animation.json"
    );

    public FirestarterAnimator() {
        super();
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> azAnimationControllerContainer) {
        azAnimationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller").build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }
}