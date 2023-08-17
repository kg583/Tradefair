package io.github.kg583.tradefair.decor;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import com.google.common.collect.ImmutableSet;
import io.github.kg583.tradefair.util.PointOfInterestUtil;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.apache.commons.lang3.ArrayUtils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.kg583.tradefair.Main.MOD_ID;

public class DecorType {
    public final String name;
    public final boolean personal;

    public final int value;
    public final int multiplier;
    public final int maximum;
    public final int decay;
    public final int shareDecrement;

    public final Identifier id;
    public RegistryKey<PointOfInterestType> key;

    public Identifier[] tags;

    public DecorType(String name, boolean personal, int value, int multiplier, int maximum, int decay,
                     int shareDecrement, Identifier... tags) {
        this.name = name;
        this.personal = personal;

        this.value = value;
        this.multiplier = multiplier;
        this.maximum = maximum;
        this.decay = decay;
        this.shareDecrement = shareDecrement;

        this.id = new Identifier(MOD_ID, this.name.toLowerCase());
        this.tags = tags;
    }

    public void addGossipType(EnumAdder builder) {
        builder.addEnum(this.name, this.name.toLowerCase(), this.multiplier, this.maximum, this.decay,
                this.shareDecrement);
    }

    public VillageGossipType getGossipType() {
        return ClassTinkerers.getEnum(VillageGossipType.class, this.name);
    }

    public void merge(DecorType other) {
        this.tags = ArrayUtils.addAll(this.tags, other.tags);
    }

    public void registerPointOfInterest() {
        this.key = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, this.id);

        PointOfInterestTypes.register(Registries.POINT_OF_INTEREST_TYPE, this.key, Stream.of(this.tags).flatMap(
                        (id) -> StreamSupport.stream(
                                Registries.BLOCK.iterateEntries(TagKey.of(RegistryKeys.BLOCK, id)).spliterator(),
                                false))
                .flatMap((entry) -> entry.value().getStateManager().getStates().stream())
                .collect(ImmutableSet.toImmutableSet()), 0, 1);
    }

    public void startGossip(MobEntity villager, VillagerGossips gossips) {
        int radius = 20;
        BlockPos pos = villager.getBlockPos();

        BirdNavigation navigation = new BirdNavigation(villager, villager.getWorld());
        navigation.setCanEnterOpenDoors(!this.personal);
        navigation.setCanPathThroughDoors(!this.personal);

        PlayerEntity player = villager.getWorld().getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 50, null);

        for (PointOfInterest poi : PointOfInterestUtil.getSortedPOIs((ServerWorld) villager.getWorld(), pos, radius,
                PointOfInterestStorage.OccupationStatus.ANY, this.key)) {
            Path path = navigation.findPathTo(poi.getPos(), radius);

            if (path != null && player != null) gossips.startGossip(player.getUuid(), this.getGossipType(), value);
        }
    }
}
