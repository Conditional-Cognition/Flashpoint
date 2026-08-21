package com.cogworks.flashpoint.client;

import com.cogworks.flashpoint.Flashpoint;
import com.cogworks.flashpoint.registry.ModItems;
import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
@SuppressWarnings("removal")
@EventBusSubscriber(modid = Flashpoint.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class FirestarterInputHandler {

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        Item rawItem = ModItems.ITEMS.getEntries().stream()
                .filter(holder -> holder.getId().getPath().equals("firestarter"))
                .map(DeferredHolder::get)
                .map(Item.class::cast)
                .findFirst()
                .orElse(Items.AIR);

        if (rawItem != Items.AIR && heldItem.is(rawItem)) {
            AzCommand.create("base_controller", "shoot", AzPlayBehaviors.PLAY_ONCE)
                    .sendForItem(player, heldItem);
        }
    }
}
