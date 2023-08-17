package io.github.kg583.tradefair.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Arrays;
import java.util.Comparator;

public abstract class PointOfInterestUtil {
    @SafeVarargs
    public static ImmutableList<PointOfInterest> getSortedPOIs(ServerWorld world, BlockPos pos, int radius,
                                                               PointOfInterestStorage.OccupationStatus occupationStatus,
                                                               RegistryKey<PointOfInterestType>... poiTypes) {
        return world.getPointOfInterestStorage().getInCircle(entry -> Arrays.stream(poiTypes)
                                .anyMatch(key -> Registries.POINT_OF_INTEREST_TYPE.get(key) == entry.value()), pos,
                        radius,
                        occupationStatus).sorted(Comparator.comparingDouble(poi -> poi.getPos().getSquaredDistance(pos)))
                .collect(ImmutableList.toImmutableList());
    }
}
