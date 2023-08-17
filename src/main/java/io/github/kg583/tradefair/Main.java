package io.github.kg583.tradefair;

import io.github.kg583.tradefair.decor.DecorTypes;
import io.github.kg583.tradefair.registry.TradefairMemoryModuleType;
import io.github.kg583.tradefair.registry.TradefairPointOfInterestTypes;
import io.github.kg583.tradefair.util.RunUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;

public class Main implements ModInitializer {
    public static final String MOD_ID = "tradefair";

    @Override
    public void onInitialize() {
        TradefairMemoryModuleType.register();

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            RunUtil.runSafe(TradefairPointOfInterestTypes::register);
            RunUtil.runSafe(DecorTypes::registerAllPointsOfInterest);
        });
    }
}