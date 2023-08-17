package io.github.kg583.tradefair.decor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.Identifier;

public class VanillaDecor {
    public static void bootstrap() {
        DecorTypes.putAll(ImmutableMap.of(
                "CARPETS",
                new DecorType("carpets", true, 5, 1, 30, 0, 1,
                        new Identifier("wool_carpets")),
                "DECOR_BLOCKS",
                new DecorType("decor_blocks", false, 5, 1, 25, 0, 1,
                        new Identifier("c", "glazed_terracotta")),
                "FLOWER_POTS",
                new DecorType("flower_pots", true, 5, 1, 20, 0, 1,
                        new Identifier("flower_pots")),
                "LIGHTING",
                new DecorType("lighting", false, 10, 2, 200, 0, 5,
                        new Identifier("c", "lighting")),
                "SHELVES",
                new DecorType("shelves", true, 5, 1, 20, 0, 1,
                        new Identifier("c", "bookshelves")),
                "SIGNAGE",
                new DecorType("signage", true, 5, 1, 10, 0, 1,
                        new Identifier("banners"),
                        new Identifier("all_signs")),
                "WINDOWS",
                new DecorType("window", true, 5, 1, 50, 0, 1,
                        new Identifier("c", "glass_blocks"),
                        new Identifier("c", "glass_panes"))
        ));
    }
}
