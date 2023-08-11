package io.github.kg583.tradefair;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    public static MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

    private void addGossipTypes() {
        String gossipType = remapper.mapClassName("intermediary", "net.minecraft.class_4139");
        EnumAdder builder =
                ClassTinkerers.enumBuilder(gossipType, String.class, int.class, int.class, int.class, int.class);

        builder.addEnum("CARPETS","carpets", 1, 30, 0, 1);
        builder.addEnum("DECOR_BLOCKS","decor_blocks", 1, 25, 0, 1);
        builder.addEnum("FLOWER_POTS", "flower_pots", 1, 20, 0, 1);
        builder.addEnum("GLASS", "glass", 1, 30, 0, 1);
        builder.addEnum("LIGHTING","lighting", 2, 200, 0, 5);
        builder.addEnum("SIGNAGE","signage", 1, 5, 0, 1);
        builder.build();
    }

    @Override
    public void run() {
        addGossipTypes();
    }
}
