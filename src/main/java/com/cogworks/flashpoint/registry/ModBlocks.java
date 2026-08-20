package com.cogworks.flashpoint.registry;

import com.cogworks.flashpoint.blocks.GasolineSpread;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final String MODID = "gaslib";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredHolder<Block, Block> GASOLINE_SPREAD =
            BLOCKS.register("gasoline_spread", () -> new GasolineSpread(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).noCollission().noOcclusion()
            ));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}