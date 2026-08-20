package com.cogworks.flashpoint.registry;

import com.cogworks.flashpoint.Flashpoint;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Flashpoint.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UNORTHODOX_WEAPONS_TAB = CREATIVE_MODE_TABS.register(
            "unorthodox_weapons_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.flashpoint"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.FIRESTARTER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.FIRESTARTER.get());
                    }).build());
}