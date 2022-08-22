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

public class NetworkEmitterBlockEntity extends AbstractNetworkMemberBlockEntity {
    protected @Nullable BlockPos targetPos;

    public NetworkEmitterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.NETWORK_EMITTER_BE, pos, state, 2000, 100, 100);
        this.targetPos = null;
    }

    public @Nullable BlockPos getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(@Nullable BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("amount", energyStorage.amount);
        if (targetPos != null) {
            nbt.putInt("targetX", targetPos.getX());
            nbt.putInt("targetY", targetPos.getY());
            nbt.putInt("targetZ", targetPos.getZ());
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
        targetPos = new BlockPos(nbt.getInt("targetX"), nbt.getInt("targetY"), nbt.getInt("targetZ"));
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) return;
        BlockEntity be = world.getBlockEntity(pos.offset(world.getBlockState(pos).get(FacingBlockWithEntity.FACING).getOpposite()));
        if (be != null) {
            EnergyStorage source = EnergyStorage.SIDED.find(world, pos, world.getBlockState(pos).get(FacingBlockWithEntity.FACING));
            if (source != null && !(be instanceof NetworkTerminalBlockEntity)) {
                EnergyStorageUtil.move(source, energyStorage, Long.MAX_VALUE, null);
            }
        }
        if (validate(world, this.targetPos)) {
            EnergyStorage target = EnergyStorage.SIDED.find(world, targetPos, Direction.UP);
            EnergyStorageUtil.move(energyStorage, target, Long.MAX_VALUE, null);
        }
        markDirty();
    }
}
