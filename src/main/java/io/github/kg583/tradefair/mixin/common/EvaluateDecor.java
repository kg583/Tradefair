package io.github.kg583.tradefair.mixin.common;

import io.github.kg583.tradefair.decor.DecorTypes;
import io.github.kg583.tradefair.registry.TradefairMemoryModuleType;
import io.github.kg583.tradefair.registry.TradefairPointOfInterestTypes;
import io.github.kg583.tradefair.util.PointOfInterestUtil;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.GlobalPos;
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

            Direction doorFacing = this.getWorld().getBlockState(poi.getPos()).get(DoorBlock.FACING);
            Path pathToFront = navigation.findPathTo(poi.getPos().add(doorFacing.getVector()), radius);

            if (pathToDoor != null) {
                this.brain.remember(TradefairMemoryModuleType.ROOM_DOOR,
                        GlobalPos.create(this.getWorld().getRegistryKey(), pos));
                poi.reserveTicket();
                return true;
            }
        }

        return false;
    }
}
