package io.github.kg583.tradefair.mixin.common;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(VillagerProfession.class)
public abstract class DeleteNitwits {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/VillagerProfession;" +
            "register(Ljava/lang/String;Ljava/util/function/Predicate;Ljava/util/function/Predicate;" +
            "Lnet/minecraft/sound/SoundEvent;)Lnet/minecraft/village/VillagerProfession;", ordinal = 1))
    private static VillagerProfession seriouslyWhyAreTheyEvenInTheGame(String id,
                                                                       Predicate<RegistryEntry<PointOfInterestType>> heldWorkstation,
                                                                       Predicate<RegistryEntry<PointOfInterestType>> acquirableWorkstation,
                                                                       @Nullable SoundEvent workSound) {

        return null;
    }
}
