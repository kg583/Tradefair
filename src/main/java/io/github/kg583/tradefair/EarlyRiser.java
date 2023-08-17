package io.github.kg583.tradefair;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import io.github.kg583.tradefair.decor.DecorTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    public static MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

    private void addGossipTypes() {
        String gossipType = remapper.mapClassName("intermediary", "net.minecraft.class_4139");
        EnumAdder builder =
                ClassTinkerers.enumBuilder(gossipType, String.class, int.class, int.class, int.class, int.class);

        DecorTypes.addAllGossipTypes(builder);
        builder.build();
    }

    @Override
    public void run() {
        addGossipTypes();
    }
}
