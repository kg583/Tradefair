package io.github.kg583.tradefair.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.kg583.tradefair.mixin.common.VillagerEntityAccessor;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;

import java.util.Optional;

import static io.github.kg583.tradefair.Main.MOD_ID;

public class NewMemoryModuleType<U> extends MemoryModuleType<U> {
    public static final MemoryModuleType<GlobalPos> HOUSE_EXTERIOR_DOOR =
            NewMemoryModuleType.register("house_exterior_door", GlobalPos.CODEC);

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, new Identifier(MOD_ID, id),
                new MemoryModuleType<>(Optional.of(codec)));
    }

    public NewMemoryModuleType(Optional<Codec<U>> codec) {
        super(codec);
    }

    public static void register() {
        VillagerEntityAccessor.setMemoryModules(
                new ImmutableList.Builder<MemoryModuleType<?>>().addAll(VillagerEntityAccessor.getMemoryModules())
                        .add(HOUSE_EXTERIOR_DOOR).build());
    }
}
