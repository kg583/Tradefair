package io.github.kg583.tradefair.mixin.common;

import io.github.kg583.tradefair.block.LodestoneBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(CompassItem.class)
public abstract class LodestoneCompassLore extends Item implements Vanishable {
    @Shadow protected abstract void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt);

    public LodestoneCompassLore(Settings settings) {
        super(settings);
    }

    @Unique
    private NbtCompound attachLore(BlockPos pos, World world, NbtCompound nbt) {
        NbtCompound display = Objects.requireNonNullElse(nbt.getCompound("display"), new NbtCompound());
        NbtList lore = new NbtList();

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof LodestoneBlockEntity) {
            Text customName = ((LodestoneBlockEntity) blockEntity).getCustomName();
            if (customName != null) lore.add(NbtString.of("{\"text\":\"" + customName.getString() + "\",\"italic" +
                    "\":\"false\"}"));
        }

        display.put("Lore", lore);
        nbt.put("display", display);

        return nbt;
    }

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CompassItem;writeNbt" +
            "(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/nbt/NbtCompound;)" +
            "V", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void attachLoreStandard(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir,
                                    BlockPos blockPos, World world, PlayerEntity playerEntity, ItemStack itemStack,
                                    boolean bl) {
        this.writeNbt(world.getRegistryKey(), blockPos, this.attachLore(blockPos, world, itemStack.getOrCreateNbt()));
        cir.setReturnValue(ActionResult.success(world.isClient));
    }

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CompassItem;writeNbt" +
            "(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/nbt/NbtCompound;)" +
            "V", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void attachLoreSpecial(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir,
                                   BlockPos blockPos, World world, PlayerEntity playerEntity, ItemStack itemStack,
                                   boolean bl, ItemStack itemStack2, NbtCompound nbtCompound) {
        this.attachLore(blockPos, world, nbtCompound);
    }
}
