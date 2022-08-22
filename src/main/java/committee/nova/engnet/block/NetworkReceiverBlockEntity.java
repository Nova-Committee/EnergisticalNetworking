package committee.nova.engnet.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

public class NetworkReceiverBlockEntity extends AbstractNetworkMemberBlockEntity {
    protected @Nullable BlockPos sourcePos;

    public @Nullable BlockPos getSourcePos() {
        return sourcePos;
    }

    public void setSourcePos(@Nullable BlockPos sourcePos) {
        this.sourcePos = sourcePos;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("amount", energyStorage.amount);
        if (sourcePos != null) {
            nbt.putInt("targetX", sourcePos.getX());
            nbt.putInt("targetY", sourcePos.getY());
            nbt.putInt("targetZ", sourcePos.getZ());
        } else {
            nbt.putInt("targetX", 0);
            nbt.putInt("targetY", 0);
            nbt.putInt("targetZ", 0);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        energyStorage.amount = nbt.getLong("amount");
        sourcePos = new BlockPos(nbt.getInt("targetX"), nbt.getInt("targetY"), nbt.getInt("targetZ"));
    }

    public NetworkReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.NETWORK_RECEIVER_BE, pos, state, 2000, 100, 100);
        this.sourcePos = null;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) return;
        if (validate(world, this.sourcePos)) {
            EnergyStorage source = EnergyStorage.SIDED.find(world, sourcePos, Direction.UP);
            EnergyStorageUtil.move(source, energyStorage, Long.MAX_VALUE, null);
        }
        BlockEntity be = world.getBlockEntity(pos.offset(world.getBlockState(pos).get(FacingBlockWithEntity.FACING).getOpposite()));
        if (be != null) {
            EnergyStorage target = EnergyStorage.SIDED.find(world, pos, world.getBlockState(pos).get(FacingBlockWithEntity.FACING));
            if (target != null && !(be instanceof NetworkTerminalBlockEntity)) {
                EnergyStorageUtil.move(energyStorage, target, Long.MAX_VALUE, null);
            }
        }
        markDirty();
    }
}
