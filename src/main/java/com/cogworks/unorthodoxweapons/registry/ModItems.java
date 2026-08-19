package com.cogworks.unorthodoxweapons.registry;

import com.cogworks.unorthodoxweapons.UnorthodoxWeapons;
import com.cogworks.unorthodoxweapons.items.*;
import com.electronwill.nightconfig.core.ConfigSpec;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UnorthodoxWeapons.MODID);

    public static final DeferredItem<FirestarterItem> FIRESTARTER = ITEMS.register(
            "firestarter",
            () -> new FirestarterItem(new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<UnobtainableItem> GASOLINE_MODEL = ITEMS.register(
            "gasoline_model",
            () -> new UnobtainableItem(new Item.Properties().stacksTo(1))
    );
    // Gasoline (Cndtnl_Cognition)
    // new mod, gonna be required lmao
}