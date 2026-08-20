package com.cogworks.flashpoint.registry;

import com.cogworks.flashpoint.Flashpoint;
import com.cogworks.flashpoint.items.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flashpoint.MODID);

    public static final DeferredItem<FirestarterItem> FIRESTARTER = ITEMS.register(
            "firestarter",
            () -> new FirestarterItem(new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<UnobtainableItem> GASOLINE_MODEL = ITEMS.register(
            "gasoline_model",
            () -> new UnobtainableItem(new Item.Properties().stacksTo(1))
    );
    // This is my weapon idea lmao
    // too big to fit in UnorthodoxWeapons
}