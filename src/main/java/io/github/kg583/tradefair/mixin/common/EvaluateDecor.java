package io.github.kg583.tradefair.mixin.common;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.common.collect.ImmutableList;
import io.github.kg583.tradefair.registry.NewMemoryModuleType;
import io.github.kg583.tradefair.registry.NewPointOfInterestTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerGossips;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

@Mixin(VillagerEntity.class)
public abstract class EvaluateDecor extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    @Shadow
    @Final
    private VillagerGossips gossip;

    public EvaluateDecor(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "wakeUp", at = @At(value = "TAIL"))
    private void evaluateDecor(CallbackInfo ci) {
        findExteriorDoor();

        generateGossip("CARPETS", true, 5, NewPointOfInterestTypes.WOOL_CARPET);
        generateGossip("DECOR_BLOCKS", false, 5, NewPointOfInterestTypes.BOOKSHELF,
                NewPointOfInterestTypes.GLAZED_TERRACOTTA);
        generateGossip("FLOWER_POTS", true, 5, NewPointOfInterestTypes.FLOWER_POT);
        generateGossip("GLASS", false, 5, NewPointOfInterestTypes.GLASS);
        generateGossip("LIGHTING", false, 20, NewPointOfInterestTypes.LIGHTING);
        generateGossip("SIGNAGE", true, 5, NewPointOfInterestTypes.BANNER, NewPointOfInterestTypes.SIGN);
    }

    @SafeVarargs
    @Unique
    private void generateGossip(String gossipType, boolean claimable, int value,
                                RegistryKey<PointOfInterestType>... poiTypes) {
        int radius = 8;
        BlockPos pos = this.getBlockPos();

        Stream<PointOfInterest> pois = ((ServerWorld) this.getWorld()).getPointOfInterestStorage().getInCircle(
                        entry -> Arrays.stream(poiTypes)
                                .anyMatch(key -> Registries.POINT_OF_INTEREST_TYPE.get(key) == entry.value()), pos,
                        radius,
                        claimable ? PointOfInterestStorage.OccupationStatus.HAS_SPACE :
                                PointOfInterestStorage.OccupationStatus.ANY)
                .sorted(Comparator.comparingDouble(poi -> poi.getPos().getSquaredDistance(pos)));

        BirdNavigation navigation = new BirdNavigation(this, this.getWorld());
        navigation.setCanEnterOpenDoors(false);
        navigation.setCanPathThroughDoors(false);

        PlayerEntity player = this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 50, null);

        for (PointOfInterest poi : pois.collect(ImmutableList.toImmutableList())) {
            Path path = navigation.findPathTo(poi.getPos(), radius);

            if (path != null) {
                if (player != null) this.gossip.startGossip(player.getUuid(),
                        ClassTinkerers.getEnum(VillageGossipType.class, gossipType), value);

                if (claimable) poi.reserveTicket();
            }
        }
    }

    @Unique
    private void findExteriorDoor() {
        int radius = 10;
        BlockPos pos = this.getBlockPos();

        Stream<PointOfInterest> pois = ((ServerWorld) this.getWorld()).getPointOfInterestStorage()
                .getInCircle(entry -> entry.matchesKey(NewPointOfInterestTypes.WOODEN_DOOR), pos, radius,
                        PointOfInterestStorage.OccupationStatus.HAS_SPACE)
                .sorted(Comparator.comparingDouble(poi -> poi.getPos().getSquaredDistance(pos)));

        for (PointOfInterest poi : pois.collect(ImmutableList.toImmutableList())) {
            Path path = this.navigation.findPathTo(poi.getPos(), radius);

            if (path != null) {
                this.brain.remember(NewMemoryModuleType.HOUSE_EXTERIOR_DOOR,
                        GlobalPos.create(this.getWorld().getRegistryKey(), pos));
                poi.reserveTicket();
            }
        }
    }
}
