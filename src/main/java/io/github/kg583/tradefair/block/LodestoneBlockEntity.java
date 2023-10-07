package io.github.kg583.tradefair.block;

import io.github.kg583.tradefair.gui.LodestoneScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import static io.github.kg583.tradefair.Main.LODESTONE_BLOCK_ENTITY;

public class LodestoneBlockEntity extends BlockEntity implements Nameable, NamedScreenHandlerFactory {
    @Nullable
    private Text customName;

    public LodestoneBlockEntity(BlockPos pos, BlockState state) {
        super(LODESTONE_BLOCK_ENTITY, pos, state);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LodestoneScreenHandler(syncId, playerInventory);
    }

    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    public Text getDisplayName() {
        return this.getName();
    }

    public Text getName() {
        return this.customName != null ? this.customName : Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }

    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }

    }
}
