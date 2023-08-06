package io.github.kg583.tradefair.mixin.common;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VillagerEntity.class)
public interface VillagerEntityAccessor {
    @Mutable
    @Accessor("MEMORY_MODULES")
    static void setMemoryModules(ImmutableList<MemoryModuleType<?>> value) {
        throw new AssertionError();
    }

    @Accessor("MEMORY_MODULES")
    static ImmutableList<MemoryModuleType<?>> getMemoryModules() {
        throw new AssertionError();
    }
}