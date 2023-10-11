package io.github.kg583.tradefair.mixin.common;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.kg583.tradefair.world.VillagerSpawner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftServer.class)
public abstract class SpawnVillagers {
    @ModifyExpressionValue(method = "createWorlds", at = @At(value = "INVOKE", target = "Lcom/google/common/collect" +
            "/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
            "Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;", ordinal = 0))
    private ImmutableList<Spawner> injectVillagerSpawner(ImmutableList<Spawner> original) {
        List<Spawner> list = new ArrayList<>(original);
        list.add(new VillagerSpawner());

        return ImmutableList.copyOf(list);
    }
}
