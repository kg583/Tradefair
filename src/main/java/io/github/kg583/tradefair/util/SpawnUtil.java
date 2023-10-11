package io.github.kg583.tradefair.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public abstract class SpawnUtil {
    @Nullable
    public static BlockPos getNearbySpawnPos(WorldView world, BlockPos pos, EntityType<?> entityType, Random random,
                                             int range) {
        for (int i = 0; i < 10; ++i) {
            int x = pos.getX() + random.nextInt(range * 2) - range;
            int z = pos.getZ() + random.nextInt(range * 2) - range;

            BlockPos blockPos = new BlockPos(x, world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z), z);
            if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos, entityType)) return blockPos;
        }
        return null;
    }

    public static boolean isSafeSpawn(WorldView world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), entityType)) {
            return false;
        }

        Box box = entityType.getDimensions().getBoxAt(pos.toCenterPos());
        BlockPos min = new BlockPos((int)Math.ceil(box.minX), (int)Math.floor(box.minY), (int)Math.ceil(box.minZ));
        BlockPos max = new BlockPos((int)Math.floor(box.maxX), (int)Math.ceil(box.maxY), (int)Math.floor(box.maxZ));

        for (BlockPos blockPos : BlockPos.iterate(min, max)) {
            if (!world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty()) return false;
        }
        return true;
    }

    public static int trySpawns(int count, ServerWorld world, BlockPos pos, EntityType<?> entityType, Random random,
                                    int range) {
        int n = 0;
        for (int i = 0; i < count; i++) {
            BlockPos spawnPos = getNearbySpawnPos(world, pos, entityType, random, range);

            if (spawnPos == null || !isSafeSpawn(world, spawnPos, entityType)) continue;

            entityType.spawn(world, spawnPos, SpawnReason.SPAWNER);
            n++;
        }
        return n;
    }
}
