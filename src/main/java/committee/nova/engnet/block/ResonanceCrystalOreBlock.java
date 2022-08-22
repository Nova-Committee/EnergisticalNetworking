package committee.nova.engnet.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ResonanceCrystalOreBlock extends OreBlock {
    public ResonanceCrystalOreBlock(Settings settings, IntProvider experience) {
        super(settings, experience);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        if (world.getRandom().nextInt(100) >= 50) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.5f, Explosion.DestructionType.DESTROY);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        spawnParticles(world, pos);
    }

    private static void spawnParticles(World world, BlockPos pos) {
        Random random = world.random;
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getOffsetX() : (double) random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getOffsetY() : (double) random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getOffsetZ() : (double) random.nextFloat();
                world.addParticle(ParticleTypes.ELECTRIC_SPARK, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos pos = hit.getBlockPos();
            world.removeBlock(pos, false);
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.5f, Explosion.DestructionType.DESTROY);
        }
    }
}
