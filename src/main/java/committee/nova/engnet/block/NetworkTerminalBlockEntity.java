package committee.nova.engnet.block;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetworkTerminalBlockEntity extends AbstractNetworkMemberBlockEntity {
    public NetworkTerminalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.NETWORK_TERMINAL_BE, pos, state, 200000, 100, 100);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("amount", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        energyStorage.amount = nbt.getLong("amount");
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        markDirty();
    }
}
