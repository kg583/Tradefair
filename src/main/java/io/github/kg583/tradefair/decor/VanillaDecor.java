package io.github.kg583.tradefair.decor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.Identifier;

public class VanillaDecor extends DecorTypes {
    public static void bootstrap() {
        DecorTypes.putAll(ImmutableMap.of(
                "CARPETS",
                new DecorType("CARPETS", true, 5, 1, 30, 0, 1,
                        new Identifier("wool_carpets")),
                "DECOR_BLOCKS",
                new DecorType("DECOR_BLOCKS", false, 5, 1, 25, 0, 1,
                        new Identifier("c", "bookshelves"),
                        new Identifier("c", "glazed_terracotta")),
                "FLOWER_POTS",
                new DecorType("FLOWER_POTS", true, 5, 1, 20, 0, 1,
                        new Identifier("flower_pots")),
                "GLASS",
                new DecorType("GLASS", true, 5, 1, 30, 0, 1,
                        new Identifier("c", "glass_blocks"),
                        new Identifier("c", "glass_panes")),
                "LIGHTING",
                new DecorType("LIGHTING", false, 20, 2, 200, 0, 5,
                        new Identifier("c", "lighting")),
                "SIGNAGE",
                new DecorType("SIGNAGE", true, 5, 1, 5, 0, 1,
                        new Identifier("banners"),
                        new Identifier("all_signs"))
        ));
    }
}
