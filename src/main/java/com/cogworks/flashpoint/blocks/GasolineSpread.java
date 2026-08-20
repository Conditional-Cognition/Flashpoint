package com.cogworks.flashpoint.blocks;

import com.cogworks.flashpoint.registry.ModBlocks;
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
        Direction clicked = ctx.getClickedFace();

        boolean up   = clicked == Direction.UP;
        boolean down = clicked == Direction.DOWN;

        boolean north = false;
        boolean east  = false;
        boolean south = false;
        boolean west  = false;

        if (clicked == Direction.NORTH) north = true;
        if (clicked == Direction.EAST)  east  = true;
        if (clicked == Direction.SOUTH) south = true;
        if (clicked == Direction.WEST)  west  = true;

        boolean northDown = computeHorizontalDown(world, pos, Direction.NORTH, north, down);
        boolean eastDown  = computeHorizontalDown(world, pos, Direction.EAST, east, down);
        boolean southDown = computeHorizontalDown(world, pos, Direction.SOUTH, south, down);
        boolean westDown  = computeHorizontalDown(world, pos, Direction.WEST, west, down);

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

        if (hasAnyFace(s)) return Blocks.AIR.defaultBlockState();
        return s;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState neighbor, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {

        boolean up    = state.getValue(UP) && canAttachToSide(world, pos, Direction.UP);
        boolean down  = state.getValue(DOWN) && canAttachToSide(world, pos, Direction.DOWN);

        boolean north = state.getValue(NORTH) && canAttachToSide(world, pos, Direction.NORTH);
        boolean east  = state.getValue(EAST)  && canAttachToSide(world, pos, Direction.EAST);
        boolean south = state.getValue(SOUTH) && canAttachToSide(world, pos, Direction.SOUTH);
        boolean west  = state.getValue(WEST)  && canAttachToSide(world, pos, Direction.WEST);

        boolean northDown = computeHorizontalDown(world, pos, Direction.NORTH, north, down);
        boolean eastDown  = computeHorizontalDown(world, pos, Direction.EAST, east, down);
        boolean southDown = computeHorizontalDown(world, pos, Direction.SOUTH, south, down);
        boolean westDown  = computeHorizontalDown(world, pos, Direction.WEST, west, down);

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

        if (hasAnyFace(s)) return Blocks.AIR.defaultBlockState();
        return s;
    }

    private boolean hasAnyFace(BlockState state) {
        return !state.getValue(UP)
                && !state.getValue(NORTH)
                && !state.getValue(EAST)
                && !state.getValue(SOUTH)
                && !state.getValue(WEST)
                && !state.getValue(DOWN)
                && !state.getValue(NORTH_DOWN)
                && !state.getValue(EAST_DOWN)
                && !state.getValue(SOUTH_DOWN)
                && !state.getValue(WEST_DOWN);
    }

    private static boolean canAttachToSide(LevelReader world, BlockPos pos, Direction face) {
        BlockPos neighbor = pos.relative(face);
        BlockState ns = world.getBlockState(neighbor);
        if (ns.getBlock() instanceof GasolineSpread) {
            return false;
        }
        return ns.isFaceSturdy(world, neighbor, face.getOpposite());
    }

    private static boolean computeHorizontalDown(LevelReader world, BlockPos pos, Direction horizontal, boolean thisSide, boolean thisDown) {
        if (!thisSide) return false;
        if (thisDown) return true;
        BlockPos below = pos.below();
        BlockState belowState = world.getBlockState(below);
        if (belowState.getBlock() instanceof GasolineSpread) {
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

    public static boolean placeSingleFace(LevelAccessor world, BlockPos pos, Direction face) {
        BlockState current = world.getBlockState(pos);
        if (!(current.getBlock() instanceof GasolineSpread)) {
            boolean canPlace = world.isEmptyBlock(pos)
                    || current.getBlock() instanceof net.minecraft.world.level.block.SnowLayerBlock
                    || current.getBlock() instanceof net.minecraft.world.level.block.BushBlock
                    || current.getBlock() instanceof net.minecraft.world.level.block.LiquidBlock;
            if (!canPlace) return false;
            placeWithClickedSide(world, pos, face);
            return true;
        }
        if (!current.getValue(propertyFor(face))) {
            boolean north = current.getValue(GasolineSpread.NORTH) || face == Direction.NORTH;
            boolean east  = current.getValue(GasolineSpread.EAST)  || face == Direction.EAST;
            boolean south = current.getValue(GasolineSpread.SOUTH) || face == Direction.SOUTH;
            boolean west  = current.getValue(GasolineSpread.WEST)  || face == Direction.WEST;
            placeWithSides(world, pos, north, east, south, west);
            return true;
        }
        return false;
    }

    public static boolean placeWithFallback(LevelAccessor world, BlockPos pos, Direction face) {
        if (placeSingleFace(world, pos, face)) return true;
        Direction[] sameBlockOrder = new Direction[] { face, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
        for (Direction d : sameBlockOrder) if (placeSingleFace(world, pos, d)) return true;
        BlockPos.MutableBlockPos m = new BlockPos.MutableBlockPos();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                m.set(pos.getX() + dx, pos.getY(), pos.getZ() + dz);
                if (placeSingleFace(world, m, face)) return true;
                if (placeSingleFace(world, m, Direction.DOWN)) return true;
                if (placeSingleFace(world, m, Direction.NORTH)) return true;
                if (placeSingleFace(world, m, Direction.EAST)) return true;
                if (placeSingleFace(world, m, Direction.SOUTH)) return true;
                if (placeSingleFace(world, m, Direction.WEST)) return true;
            }
        }
        return false;
    }

    public static void placeWithClickedSide(LevelAccessor world, BlockPos pos, Direction clickedSide) {
        BlockState bs = ModBlocks.GASOLINE_SPREAD.get().defaultBlockState();

        boolean up   = canAttachToSide(world, pos, Direction.UP);
        boolean down = canAttachToSide(world, pos, Direction.DOWN);

        boolean north = clickedSide == Direction.NORTH;
        boolean east  = clickedSide == Direction.EAST;
        boolean south = clickedSide == Direction.SOUTH;
        boolean west  = clickedSide == Direction.WEST;

        boolean northDown = computeHorizontalDown(world, pos, Direction.NORTH, north, down);
        boolean eastDown  = computeHorizontalDown(world, pos, Direction.EAST,  east,  down);
        boolean southDown = computeHorizontalDown(world, pos, Direction.SOUTH, south, down);
        boolean westDown  = computeHorizontalDown(world, pos, Direction.WEST,  west,  down);

        BlockState finalState = bs
                .setValue(UP, up).setValue(DOWN, down)
                .setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west)
                .setValue(NORTH_DOWN, northDown).setValue(EAST_DOWN, eastDown).setValue(SOUTH_DOWN, southDown).setValue(WEST_DOWN, westDown);

        world.setBlock(pos, finalState, 3);
    }

    public static void placeWithSides(LevelAccessor world, BlockPos pos,
                                      boolean north, boolean east, boolean south, boolean west) {
        BlockState bs = ModBlocks.GASOLINE_SPREAD.get().defaultBlockState();

        boolean up   = canAttachToSide(world, pos, Direction.UP);
        boolean down = canAttachToSide(world, pos, Direction.DOWN);

        boolean northDown = computeHorizontalDown(world, pos, Direction.NORTH, north, down);
        boolean eastDown  = computeHorizontalDown(world, pos, Direction.EAST,  east,  down);
        boolean southDown = computeHorizontalDown(world, pos, Direction.SOUTH, south, down);
        boolean westDown  = computeHorizontalDown(world, pos, Direction.WEST,  west,  down);

        BlockState finalState = bs
                .setValue(UP, up).setValue(DOWN, down)
                .setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west)
                .setValue(NORTH_DOWN, northDown).setValue(EAST_DOWN, eastDown).setValue(SOUTH_DOWN, southDown).setValue(WEST_DOWN, westDown);

        world.setBlock(pos, finalState, 3);
    }

    private static final double THICK = 0.01;

    private static VoxelShape faceShape(BlockState state) {
        VoxelShape s = Shapes.empty();
        // top
        if (state.getValue(UP)) {
            s = Shapes.or(s, Block.box(0, 16 - THICK * 16, 0, 16, 16, 16));
        }
        // bottom
        if (state.getValue(DOWN)) {
            s = Shapes.or(s, Block.box(0, 0, 0, 16, THICK * 16, 16));
        }
        // north (negative Z)
        if (state.getValue(NORTH)) {
            s = Shapes.or(s, Block.box(0, 0, 0, 16, 16, THICK * 16));
        }
        // south (positive Z)
        if (state.getValue(SOUTH)) {
            s = Shapes.or(s, Block.box(0, 0, 16 - THICK * 16, 16, 16, 16));
        }
        // west (negative X)
        if (state.getValue(WEST)) {
            s = Shapes.or(s, Block.box(0, 0, 0, THICK * 16, 16, 16));
        }
        // east (positive X)
        if (state.getValue(EAST)) {
            s = Shapes.or(s, Block.box(16 - THICK * 16, 0, 0, 16, 16, 16));
        }
        return s;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return faceShape(state);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return faceShape(state);
    }
}