package io.github.kg583.tradefair.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class VillagerUtil {
    public static Map<VillagerProfession, List<VillagerEntity>> getEmployment(ServerWorld world, BlockPos pos,
                                                                           int radius) {
        Map<VillagerProfession, List<VillagerEntity>> employment = new HashMap<>();

        for (VillagerEntity villager : world.getEntitiesByType(EntityType.VILLAGER, new Box(pos).expand(radius),
                Entity::isAlive)) {
            VillagerProfession profession = villager.getVillagerData().getProfession();

            if (employment.containsKey(profession)) {
                employment.get(profession).add(villager);
            } else {
                employment.put(profession, new ArrayList<>(List.of(villager)));
            }
        }

        return employment;
    }
}
