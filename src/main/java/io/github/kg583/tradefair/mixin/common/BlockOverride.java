package io.github.kg583.tradefair.mixin.common;

import io.github.kg583.tradefair.block.Lodestone;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Blocks.class)
public abstract class BlockOverride {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "Lnet/minecraft/block/Block;", ordinal = 126))
    private static Block overrideLodestone(AbstractBlock.Settings settings) {
        return new Lodestone();
    }
}
