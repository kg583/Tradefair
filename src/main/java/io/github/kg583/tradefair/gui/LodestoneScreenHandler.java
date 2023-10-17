package io.github.kg583.tradefair.gui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.kg583.tradefair.Main.LODESTONE_SCREEN_HANDLER;

public class LodestoneScreenHandler extends ScreenHandler {
    protected Map<VillagerProfession, List<Entity>> employment;

    public LodestoneScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory);

        employment =
                buf.readMap((packetByteBuf -> Registries.VILLAGER_PROFESSION.get(new Identifier(packetByteBuf.readString()))),
                        (packetByteBuf -> {
                            List<Entity> villagerEntities = new ArrayList<>();

                            int size = packetByteBuf.readInt();
                            for (int i = 0; i < size; i++) {
                                villagerEntities.add(playerInventory.player.getEntityWorld().getEntityLookup().get(packetByteBuf.readUuid()));
                            }

                            return villagerEntities;
                        }));
    }

    public LodestoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(LODESTONE_SCREEN_HANDLER, syncId);

        employment = new HashMap<>();
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
