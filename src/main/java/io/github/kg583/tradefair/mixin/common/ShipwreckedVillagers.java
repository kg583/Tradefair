package io.github.kg583.tradefair.mixin.common;

import io.github.kg583.tradefair.util.SpawnUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.structure.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShipwreckGenerator.Piece.class)
public abstract class ShipwreckedVillagers extends SimpleStructurePiece {
    @Final
    @Shadow
    private boolean grounded;

    public ShipwreckedVillagers(StructurePieceType type, int length, StructureTemplateManager structureTemplateManager,
                                Identifier id, String template, StructurePlacementData placementData, BlockPos pos) {
        super(type, length, structureTemplateManager, id, template, placementData, pos);
    }

    @Inject(method = "generate", at = @At(value = "TAIL"))
    private void spawnOnBeach(StructureWorldAccess world, StructureAccessor structureAccessor,
                              ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos,
                              BlockPos pivot, CallbackInfo ci) {
        if (!this.grounded || random.nextInt(4) != 0) {
            return;
        }

        int count = random.nextInt(3);
        for (int x = 0; x < count; x++) {
            BlockPos spawnPos = SpawnUtil.getNearbySpawnPos(world, this.pos, EntityType.VILLAGER, random, 8);
            if (spawnPos == null || !SpawnUtil.doesNotSuffocateAt(world, spawnPos, EntityType.VILLAGER)) continue;

            VillagerEntity entity = new VillagerEntity(EntityType.VILLAGER, world.toServerWorld());
            entity.refreshPositionAndAngles(spawnPos, 0, 0);
            entity.initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.SPAWNER, null, null);
            world.spawnEntity(entity);
        }
    }
}
