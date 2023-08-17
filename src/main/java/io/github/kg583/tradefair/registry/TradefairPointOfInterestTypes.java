package io.github.kg583.tradefair.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.kg583.tradefair.Main.MOD_ID;

public class TradefairPointOfInterestTypes extends PointOfInterestTypes {
    public static final RegistryKey<PointOfInterestType> WOODEN_DOOR =
            RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(MOD_ID, "wooden_door"));

    @SafeVarargs
    private static void registerFromTags(RegistryKey<PointOfInterestType> key, boolean claimable,
                                         TagKey<Block>... tags) {
        register(Registries.POINT_OF_INTEREST_TYPE, key, Stream.of(tags)
                .flatMap((tag) -> StreamSupport.stream(Registries.BLOCK.iterateEntries(tag).spliterator(), false))
                .flatMap((entry) -> entry.value().getStateManager().getStates().stream())
                .collect(ImmutableSet.toImmutableSet()), claimable ? 1 : 0, 1);
    }

    public static void register() {
        registerFromTags(WOODEN_DOOR, true, BlockTags.WOODEN_DOORS);

        VillagerEntity.POINTS_OF_INTEREST.put(TradefairMemoryModuleType.ROOM_DOOR,
                (villagerEntity, pointOfInterestType) -> Objects.equals(pointOfInterestType,
                        RegistryEntry.of(Registries.POINT_OF_INTEREST_TYPE.get(WOODEN_DOOR))));
    }
}
