package io.github.kg583.tradefair.registry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.kg583.tradefair.mixin.common.VillagerEntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.kg583.tradefair.Main.MOD_ID;

public class NewPointOfInterestTypes extends PointOfInterestTypes {
    public static final RegistryKey<PointOfInterestType> LIGHTING = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE,
            new Identifier(MOD_ID, "lighting"));

    public static final RegistryKey<PointOfInterestType> ALL_SIGNS = of("all_signs");
    public static final RegistryKey<PointOfInterestType> BANNER = of("banner");
    public static final RegistryKey<PointOfInterestType> BOOKSHELF = of("bookshelf");
    public static final RegistryKey<PointOfInterestType> GLASS = of("glass");
    public static final RegistryKey<PointOfInterestType> GLAZED_TERRACOTTA = of("glazed_terracotta");
    public static final RegistryKey<PointOfInterestType> FLOWER_POT = of("flower_pot");
    public static final RegistryKey<PointOfInterestType> WOODEN_DOOR = of("wooden_door");
    public static final RegistryKey<PointOfInterestType> WOOL_CARPET = of("wool_carpet");

    @SafeVarargs
    private static void registerFromTags(RegistryKey<PointOfInterestType> key, TagKey<Block>... tags) {
        register(Registries.POINT_OF_INTEREST_TYPE, key, Stream.of(tags)
                .flatMap((tag) -> StreamSupport.stream(Registries.BLOCK.iterateEntries(tag).spliterator(), false))
                .flatMap((entry) -> entry.value().getStateManager().getStates().stream())
                .collect(ImmutableSet.toImmutableSet()), 0, 1);
    }

    private static void registerFromTags(RegistryKey<PointOfInterestType> key, Identifier... ids) {
        register(Registries.POINT_OF_INTEREST_TYPE, key, Stream.of(ids).flatMap((id) -> StreamSupport.stream(
                        Registries.BLOCK.iterateEntries(TagKey.of(RegistryKeys.BLOCK, id)).spliterator(), false))
                .flatMap((entry) -> entry.value().getStateManager().getStates().stream())
                .collect(ImmutableSet.toImmutableSet()), 0, 1);
    }

    public static void register() {
        registerFromTags(ALL_SIGNS, BlockTags.ALL_SIGNS);
        registerFromTags(BANNER, BlockTags.BANNERS);
        registerFromTags(BOOKSHELF, new Identifier("c", "bookshelves"));
        registerFromTags(FLOWER_POT, BlockTags.FLOWER_POTS);
        registerFromTags(GLASS, new Identifier("c", "glass"));
        registerFromTags(GLAZED_TERRACOTTA, new Identifier("c", "glazed_terracottas"));
        registerFromTags(WOODEN_DOOR, BlockTags.WOODEN_DOORS);
        registerFromTags(WOOL_CARPET, BlockTags.WOOL_CARPETS);

        registerFromTags(LIGHTING, new Identifier("tradefair", "lighting"));

        VillagerEntity.POINTS_OF_INTEREST.put(NewMemoryModuleType.HOUSE_EXTERIOR_DOOR,
                (villagerEntity, pointOfInterestType) -> Objects.equals(pointOfInterestType,
                        RegistryEntry.of(Registries.POINT_OF_INTEREST_TYPE.get(WOODEN_DOOR))));
    }
}
