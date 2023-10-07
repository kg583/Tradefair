package io.github.kg583.tradefair.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

import static io.github.kg583.tradefair.Main.LODESTONE_SCREEN_HANDLER;

public class LodestoneScreenHandler extends ScreenHandler {
    public LodestoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public LodestoneScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(LODESTONE_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
