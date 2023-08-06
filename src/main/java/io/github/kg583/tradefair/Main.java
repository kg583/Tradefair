package io.github.kg583.tradefair;

import io.github.kg583.tradefair.registry.PointOfInterestTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;

public class Main implements ModInitializer {
    public static final String MOD_ID = "tradefair";

    @Override
    public void onInitialize() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            try {
                PointOfInterestTypes.register();
            } catch (RuntimeException ignored) {

            }
        });
    }
}