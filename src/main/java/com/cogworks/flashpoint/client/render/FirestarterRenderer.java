package com.cogworks.flashpoint.client.render;

import com.cogworks.flashpoint.Flashpoint;
import com.cogworks.flashpoint.animators.FirestarterAnimator;
import mod.azure.azurelib.rewrite.animation.impl.AzItemAnimator;
import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;

public class FirestarterRenderer extends AzItemRenderer {
    private static final AzItemRendererConfig CONFIG = AzItemRendererConfig.builder(
                    ResourceLocation.fromNamespaceAndPath(Flashpoint.MODID, "geo/item/firestarter.geo.json"),
                    ResourceLocation.fromNamespaceAndPath(Flashpoint.MODID, "textures/item/firestarter.png")
            )
            .useNewOffset(true)
            .build();

    public FirestarterRenderer() {
        super(CONFIG);
    }

    @Override
    public AzItemAnimator getAnimator() {
        return new FirestarterAnimator();
    }
}