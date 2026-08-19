package com.cogworks.gaslib.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class GasolineSpread extends Block {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    // Per-horizontal derived properties used when DOWN is true OR the below spread provides the horizontal face.
    public static final BooleanProperty NORTH_DOWN = BooleanProperty.create("north_down");
    public static final BooleanProperty EAST_DOWN  = BooleanProperty.create("east_down");
    public static final BooleanProperty SOUTH_DOWN = BooleanProperty.create("south_down");
    public static final BooleanProperty WEST_DOWN  = BooleanProperty.create("west_down");

    public GasolineSpread(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(UP, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(DOWN, false)
                .setValue(NORTH_DOWN, false)
                .setValue(EAST_DOWN, false)
                .setValue(SOUTH_DOWN, false)
                .setValue(WEST_DOWN, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST, DOWN, NORTH_DOWN, EAST_DOWN, SOUTH_DOWN, WEST_DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelReader world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        // Recompute everything
        boolean up    = canAttachOrLinked(world, pos, Direction.UP);
        boolean down  = canAttachOrLinked(world, pos, Direction.DOWN);
        boolean north = canAttachOrLinked(world, pos, Direction.NORTH);
        boolean east  = canAttachOrLinked(world, pos, Direction.EAST);
        boolean south = canAttachOrLinked(world, pos, Direction.SOUTH);
        boolean west  = canAttachOrLinked(world, pos, Direction.WEST);

        boolean northDown = computeHorizontalDown(world, pos, Direction.NORTH, down);
        boolean eastDown  = computeHorizontalDown(world, pos, Direction.EAST,  down);
        boolean southDown = computeHorizontalDown(world, pos, Direction.SOUTH, down);
        boolean westDown  = computeHorizontalDown(world, pos, Direction.WEST,  down);

        BlockState s = this.defaultBlockState()
                .setValue(UP, up)
                .setValue(DOWN, down)
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(NORTH_DOWN, northDown)
                .setValue(EAST_DOWN, eastDown)
                .setValue(SOUTH_DOWN, southDown)
                .setValue(WEST_DOWN, westDown);

        if (!hasAnyFace(s)) return Blocks.AIR.defaultBlockState();
        return s;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState neighbor, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        // recompute all faces every time a neighbor changes
        LevelReader reader = world;
        boolean up    = canAttachOrLinked(reader, pos, Direction.UP);
        boolean down  = canAttachOrLinked(reader, pos, Direction.DOWN);
        boolean north = canAttachOrLinked(reader, pos, Direction.NORTH);
        boolean east  = canAttachOrLinked(reader, pos, Direction.EAST);
        boolean south = canAttachOrLinked(reader, pos, Direction.SOUTH);
        boolean west  = canAttachOrLinked(reader, pos, Direction.WEST);

        boolean northDown = computeHorizontalDown(reader, pos, Direction.NORTH, down);
        boolean eastDown  = computeHorizontalDown(reader, pos, Direction.EAST,  down);
        boolean southDown = computeHorizontalDown(reader, pos, Direction.SOUTH, down);
        boolean westDown  = computeHorizontalDown(reader, pos, Direction.WEST,  down);

        BlockState s = state
                .setValue(UP, up)
                .setValue(DOWN, down)
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(NORTH_DOWN, northDown)
                .setValue(EAST_DOWN, eastDown)
                .setValue(SOUTH_DOWN, southDown)
                .setValue(WEST_DOWN, westDown);

        if (!hasAnyFace(s)) return Blocks.AIR.defaultBlockState();
        return s;
    }

    private boolean hasAnyFace(BlockState state) {
        return state.getValue(UP)
                || state.getValue(NORTH)
                || state.getValue(EAST)
                || state.getValue(SOUTH)
                || state.getValue(WEST)
                || state.getValue(DOWN)
                || state.getValue(NORTH_DOWN)
                || state.getValue(EAST_DOWN)
                || state.getValue(SOUTH_DOWN)
                || state.getValue(WEST_DOWN);
    }

    /**
     * Returns true if the side can attach to a sturdy face.
     */
    private static boolean canAttachToSide(LevelReader world, BlockPos pos, Direction face) {
        BlockPos neighbor = pos.relative(face);
        BlockState ns = world.getBlockState(neighbor);
        return ns.isFaceSturdy(world, neighbor, face.getOpposite());
    }

    /**
     * True if the side can attach to a sturdy face OR is linked by another GasolineSpread in the adjacent spot:
     * - DOWN is true if underside is sturdy OR the block below is GasolineSpread with UP true
     * - UP is true if top is sturdy OR the block above is GasolineSpread with DOWN true
     * - horizontals are only direct attach checks here
     */
    private static boolean canAttachOrLinked(LevelReader world, BlockPos pos, Direction face) {
        if (canAttachToSide(world, pos, face)) return true;

        if (face == Direction.DOWN) {
            BlockPos below = pos.below();
            BlockState bs = world.getBlockState(below);
            if (bs.getBlock() instanceof GasolineSpread && bs.getValue(UP)) return true;
        }

        if (face == Direction.UP) {
            BlockPos above = pos.above();
            BlockState bs = world.getBlockState(above);
            if (bs.getBlock() instanceof GasolineSpread && bs.getValue(DOWN)) return true;
        }

        return false;
    }

    /**
     * Helper to set e.g. NORTH_DOWN. True when either:
     * - this block's DOWN is true
     * - OR the block below is a GasolineSpread with the horizontal face (north/east/etc) true
     */
    private static boolean computeHorizontalDown(LevelReader world, BlockPos pos, Direction horizontal, boolean thisDown) {
        if (thisDown) return true;
        BlockPos below = pos.below();
        BlockState belowState = world.getBlockState(below);
        if (belowState.getBlock() instanceof GasolineSpread) {
            // safe to read the horizontal property because it's defined on GasolineSpread
            return belowState.getValue(propertyFor(horizontal));
        }
        return false;
    }

    private static BooleanProperty propertyFor(Direction d) {
        return switch (d) {
            case UP -> UP;
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case DOWN -> DOWN;
        };
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return Shapes.empty();
    }
}