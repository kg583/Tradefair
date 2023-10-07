package io.github.kg583.tradefair.mixin.common;

import io.github.kg583.tradefair.decor.DecorTypes;
import io.github.kg583.tradefair.registry.TradefairMemoryModuleType;
import io.github.kg583.tradefair.registry.TradefairPointOfInterestTypes;
import io.github.kg583.tradefair.util.PointOfInterestUtil;
import io.github.kg583.tradefair.util.UUIDUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerGossips;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerHappiness extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    @Shadow
    @Final
    private VillagerGossips gossip;

    public VillagerHappiness(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "sleep", at = @At(value = "TAIL"))
    private void dislikeSleepingOutside(BlockPos pos, CallbackInfo ci) {
        BirdNavigation navigation = new BirdNavigation(this, this.getWorld());
        navigation.setCanEnterOpenDoors(false);
        navigation.setCanPathThroughDoors(false);

        Path pathToSky = navigation.findPathTo(pos.add(0, 20, 0), 40);
        // if (pathToSky != null) this.gossip.startGossip(UUIDUtil.NIL, VillageGossipType.MINOR_NEGATIVE, 25);
    }

    @Inject(method = "wakeUp", at = @At(value = "TAIL"))
    private void evaluateDecor(CallbackInfo ci) {
        if (!findDoor()) return;

        DecorTypes.startAllGossips(this, this.gossip);
    }

    @Unique
    private boolean findDoor() {
        int radius = 20;
        BlockPos pos = this.getBlockPos();

        for (PointOfInterest poi : PointOfInterestUtil.getSortedPOIs((ServerWorld) this.getWorld(), pos, radius,
                PointOfInterestStorage.OccupationStatus.ANY, TradefairPointOfInterestTypes.WOODEN_DOOR)) {
            MobNavigation navigation = new MobNavigation(this, this.getWorld());
            navigation.setCanEnterOpenDoors(false);
            navigation.setCanPathThroughDoors(false);

            Path pathToDoor = navigation.findPathTo(poi.getPos(), radius);

            if (pathToDoor != null) {
                this.brain.remember(TradefairMemoryModuleType.ROOM_DOOR,
                        GlobalPos.create(this.getWorld().getRegistryKey(), pos));
                poi.reserveTicket();
                return true;
            }
        }

        return false;
    }

    @Inject(method = "getReputation", at = @At(value = "HEAD"), cancellable = true)
    private void implementHappiness(PlayerEntity player, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.gossip.getReputationFor(player.getUuid(), (gossipType) -> true) +
                this.gossip.getReputationFor(UUIDUtil.NIL, (gossipType) -> true));
    }
}
