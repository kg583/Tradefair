package io.github.kg583.tradefair.mixin.common;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.kg583.tradefair.registry.NewMemoryModuleType;
import io.github.kg583.tradefair.registry.NewPointOfInterestTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Stream;

@Mixin(VillagerEntity.class)
public abstract class FindExteriorDoor extends MerchantEntity implements InteractionObserver, VillagerDataContainer {

    public FindExteriorDoor(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "wakeUp", at = @At(value = "TAIL"))
    private void searchForDoor(CallbackInfo ci) {
        int radius = 10;

        Stream<Pair<RegistryEntry<PointOfInterestType>, BlockPos>>
                pois = ((ServerWorld) this.getWorld()).getPointOfInterestStorage()
                .getSortedTypesAndPositions(entry -> entry.matchesKey(NewPointOfInterestTypes.WOODEN_DOOR), entry -> true,
                        this.getBlockPos(), radius, PointOfInterestStorage.OccupationStatus.ANY);

        for (Pair<RegistryEntry<PointOfInterestType>, BlockPos> pair : pois.collect(ImmutableList.toImmutableList())) {
            BlockPos pos = pair.getSecond();

            Path path = this.navigation.findPathTo(pos, radius);

            if (path != null) {
                this.brain.remember(NewMemoryModuleType.HOUSE_EXTERIOR_DOOR,
                        GlobalPos.create(this.getWorld().getRegistryKey(), pos));
            }
            this.navigation.stop();
        }


    }
}
