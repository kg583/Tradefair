package io.github.kg583.tradefair.mixin.common;

import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleRegistry.class)
public abstract class ThawedSimpleRegistry<T> implements MutableRegistry<T> {
    @Shadow
    private boolean frozen;

    @Redirect(method = "set(ILnet/minecraft/registry/RegistryKey;Ljava/lang/Object;" +
            "Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/registry/entry/RegistryEntry$Reference;", at =
            @At(value = "INVOKE", target = "Lnet/minecraft/registry/SimpleRegistry;assertNotFrozen(Lnet/minecraft/registry/RegistryKey;)V"))
    private void thaw(SimpleRegistry<T> owner, RegistryKey<T> key) {}

    @Inject(method = "freeze", at = @At(value = "HEAD"))
    private void refreeze(CallbackInfoReturnable<Registry<T>> cir) {
        this.frozen = false;
    }
}
