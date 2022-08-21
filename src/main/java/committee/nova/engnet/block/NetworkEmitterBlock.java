package committee.nova.engnet.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NetworkEmitterBlock extends FacingNetworkMemberBlockWithEntity {
    public NetworkEmitterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NetworkEmitterBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.NETWORK_EMITTER_BE, (world1, pos, state1, be) -> be.tick(world1, pos, state1));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case NORTH -> VoxelShapes.cuboid(0.25f, 0.1875f, 0.9375f, 0.75f, 0.8125f, 1.0f);
            case SOUTH -> VoxelShapes.cuboid(0.25f, 0.1875f, 0.0f, 0.75f, 0.8125f, 0.0625f);
            case EAST -> VoxelShapes.cuboid(0.0f, 0.1875f, 0.25f, 0.0625f, 0.8125f, 0.75f);
            case WEST -> VoxelShapes.cuboid(0.9375f, 0.1875f, 0.25f, 1.0f, 0.8125f, 0.75f);
            case UP -> VoxelShapes.cuboid(0.25f, 0.0f, 0.1875f, 0.75f, 0.0625f, 0.8125f);
            case DOWN -> VoxelShapes.cuboid(0.25f, 0.9375f, 0.1875f, 0.75f, 1.0f, 0.8125f);
        };
    }
}
