package committee.nova.engnet.block;

import committee.nova.engnet.util.Tickable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNetworkMemberBlockEntity extends BlockEntity implements Tickable {
    protected final long capacity;
    protected final long maxInsert;
    protected final long maxExtract;
    protected final SimpleEnergyStorage energyStorage;

    public AbstractNetworkMemberBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long capacity, long maxInsert, long maxExtract) {
        super(type, pos, state);
        this.capacity = capacity;
        this.maxInsert = maxInsert;
        this.maxExtract = maxExtract;
        energyStorage = new SimpleEnergyStorage(capacity, maxInsert, maxExtract) {
            @Override
            protected void onFinalCommit() {
                markDirty();
            }
        };
    }

    public long getCapacity() {
        return capacity;
    }

    public long getMaxInsert() {
        return maxInsert;
    }

    public long getMaxExtract() {
        return maxExtract;
    }

    protected static boolean validate(World world, BlockPos pos) {
        if (pos == null) return false;
        List<Boolean> valid = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            EnergyStorage storage = EnergyStorage.SIDED.find(world, pos, dir);
            valid.add(storage != null && world.getBlockEntity(pos) instanceof NetworkTerminalBlockEntity);
        }
        return !valid.contains(false);
    }
}
