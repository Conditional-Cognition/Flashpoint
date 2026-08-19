package com.cogworks.unorthodoxweapons.registry;

import com.cogworks.unorthodoxweapons.UnorthodoxWeapons;/*
import net.minecraft.Util;*/
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;/*
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;*/
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnorthodoxWeapons.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UNORTHODOX_WEAPONS_TAB = CREATIVE_MODE_TABS.register(
            "unorthodox_weapons_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.unorthodoxweapons"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.FIREBRAND.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        //output.accept(ModItems.POISON_TRIDENT.get());
                        output.accept(ModItems.SPEAR_OF_LONGINUS.get());
                        output.accept(ModItems.ADMIN_TOOL.get());
                        output.accept(ModItems.FIREBRAND.get());
                        output.accept(ModItems.FIREBRAND_COMPONENT.get());
                        output.accept(ModItems.MOCHI_HAMMER.get());
                        output.accept(ModItems.BLOCK_HOLE_LAUNCHER.get());
                    }).build());
}