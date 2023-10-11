package io.github.kg583.tradefair.world;

import io.github.kg583.tradefair.util.SpawnUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.Spawner;

public class VillagerSpawner implements Spawner {
    private final int MIN_PLAYER_DISTANCE = 10;
    private final int MAX_PLAYER_DISTANCE = 30;

    private final int MIN_VILLAGE_DISTANCE = 48;

    private final int DELAY = 12000;
    private final int VARIANCE = 1200;

    private int cooldown;

    public VillagerSpawner() {
    }

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        Random random = world.random;
        --this.cooldown;
        if (this.cooldown > 0 || random.nextInt(5) != 0) {
            return 0;
        }

        this.cooldown += DELAY + random.nextInt(VARIANCE);
        if (world.getTimeOfDay() / 24000L < 5L || !world.isDay()) {
            return 0;
        }

        int numPlayers = world.getPlayers().size();
        if (numPlayers < 1) {
            return 0;
        }
        PlayerEntity playerEntity = world.getPlayers().get(random.nextInt(numPlayers));
        if (playerEntity.isSpectator()) {
            return 0;
        }
        if (world.isNearOccupiedPointOfInterest(playerEntity.getBlockPos(), MIN_VILLAGE_DISTANCE)) {
            return 0;
        }

        int dx = (MIN_PLAYER_DISTANCE + random.nextInt(MAX_PLAYER_DISTANCE - MIN_PLAYER_DISTANCE)) * (random.nextBoolean() ? -1 : 1);
        int dz = (MIN_PLAYER_DISTANCE + random.nextInt(MAX_PLAYER_DISTANCE - MIN_PLAYER_DISTANCE)) * (random.nextBoolean() ? -1 : 1);
        BlockPos pos = playerEntity.getBlockPos().mutableCopy().move(dx, 0, dz);

        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
        if (!registryEntry.isIn(BiomeTags.IS_FOREST)) {
            return 0;
        }

        return SpawnUtil.trySpawns(random.nextInt(4), world, pos, EntityType.VILLAGER, random, 4);
    }
}
