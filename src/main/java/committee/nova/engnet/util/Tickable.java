package committee.nova.engnet.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Tickable {
    void tick(World world, BlockPos pos, BlockState state);
}
