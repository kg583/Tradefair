package io.github.kg583.tradefair.mixin.common;

import io.github.kg583.tradefair.util.SpawnUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(WanderingTraderManager.class)
public abstract class WanderingVillagers implements Spawner {
    @Final
    @Shadow
    private Random random;

    @Inject(method = "trySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WanderingTraderManager;" +
            "spawnLlama(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/WanderingTraderEntity;" +
            "I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void spawnVillagers(ServerWorld world, CallbackInfoReturnable<Boolean> cir, PlayerEntity playerEntity,
                                BlockPos blockPos, int i, PointOfInterestStorage pointOfInterestStorage,
                                Optional optional, BlockPos blockPos2, BlockPos blockPos3,
                                WanderingTraderEntity wanderingTraderEntity, int j) {
        if (this.random.nextInt(5) == 0) {
            SpawnUtil.trySpawns(1, world, wanderingTraderEntity.getBlockPos(), EntityType.VILLAGER,
                    this.random, 4);
        }
    }
}
