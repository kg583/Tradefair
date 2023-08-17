package io.github.kg583.tradefair.decor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.Identifier;

public class AnotherFurnitureDecor {
    public static void bootstrap() {
        DecorTypes.putAll(ImmutableMap.of(
                "AWNINGS",
                new DecorType("awnings", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "awnings")),
                "BENCHES",
                new DecorType("benches", false, 5, 1, 40, 0, 1,
                        new Identifier("another_furniture", "benches")),
                "CHAIRS",
                new DecorType("chairs", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "chairs")),
                "CLOCKS",
                new DecorType("clocks", true, 5, 1, 10, 0, 1,
                        new Identifier("another_furniture", "grandfather_clocks")),
                "DRAWERS",
                new DecorType("drawers", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "drawers")),
                "FLOWER_POTS",
                new DecorType("flower_pots", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "flower_boxes")),
                "SHELVES",
                new DecorType("shelves", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "shelves")),
                "SOFAS",
                new DecorType("sofas", true, 5, 1, 10, 0, 1,
                        new Identifier("another_furniture", "sofas")),
                "STOOLS",
                new DecorType("stools", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "stools"),
                        new Identifier("another_furniture", "tall_stools")),
                "TABLES",
                new DecorType("tables", true, 5, 1, 15, 0, 1,
                        new Identifier("another_furniture", "tables"))
        ));

        DecorTypes.putAll(ImmutableMap.of(
                "WINDOW_DECOR",
                new DecorType("window_decor", true, 5, 1, 20, 0, 1,
                        new Identifier("another_furniture", "curtains"),
                        new Identifier("another_furniture", "shutters"))
        ));
    }
}
