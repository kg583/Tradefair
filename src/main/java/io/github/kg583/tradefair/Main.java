package io.github.kg583.tradefair;

import io.github.kg583.tradefair.registry.NewMemoryModuleType;
import io.github.kg583.tradefair.registry.NewPointOfInterestTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;

public class Main implements ModInitializer {
    public static final String MOD_ID = "tradefair";

    @Override
    public void onInitialize() {
        NewMemoryModuleType.register();

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            try {
                NewPointOfInterestTypes.register();
            } catch (RuntimeException ignored) {

            }
        });
    }
}