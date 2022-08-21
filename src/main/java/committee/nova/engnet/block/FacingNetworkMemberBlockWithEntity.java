package committee.nova.engnet.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

public abstract class FacingNetworkMemberBlockWithEntity extends FacingBlockWithEntity {
    public FacingNetworkMemberBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(state.get(FACING).getOpposite());
        return EnergyStorage.SIDED.find((World) world, blockPos, state.get(FACING).getOpposite()) != null;
    }

    @Nullable
    public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);
}
