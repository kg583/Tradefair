package io.github.kg583.tradefair.decor;

import com.chocohead.mm.api.EnumAdder;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.village.VillagerGossips;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.github.kg583.tradefair.util.DecorConfig.EXCLUDE_DECORS;

public class DecorTypes {
    private static final Map<String, DecorType> TYPES = new HashMap<>();

    public static void addAllGossipTypes(EnumAdder builder) {
        TYPES.forEach((name, type) -> type.addGossipType(builder));
    }

    public static DecorType getDecorType(String name) {
        return TYPES.get(name);
    }

    public static void putAll(Map<String, DecorType> types) {
        types.forEach((name, type) -> {
            if (TYPES.containsKey(name)) {
                TYPES.get(name).merge(type);
            } else {
                TYPES.put(name, type);
            }
        });
    }

    public static void registerAllPointsOfInterest() {
        TYPES.forEach((name, type) -> type.registerPointOfInterest());
    }

    public static void startAllGossips(MobEntity villager, VillagerGossips gossips) {
        TYPES.forEach((name, type) -> {
            if (!Arrays.asList(EXCLUDE_DECORS).contains(name)) type.startGossip(villager, gossips);
        });
    }

    static {
        AnotherFurnitureDecor.bootstrap();
        VanillaDecor.bootstrap();
    }
}
