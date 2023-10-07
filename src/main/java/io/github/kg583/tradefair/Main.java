package io.github.kg583.tradefair;

import io.github.kg583.tradefair.block.LodestoneBlockEntity;
import io.github.kg583.tradefair.decor.DecorTypes;
import io.github.kg583.tradefair.gui.LodestoneScreenHandler;
import io.github.kg583.tradefair.registry.TradefairMemoryModuleType;
import io.github.kg583.tradefair.registry.TradefairPointOfInterestTypes;
import io.github.kg583.tradefair.util.RunUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
    public static final String MOD_ID = "tradefair";

    public static final BlockEntityType<LodestoneBlockEntity> LODESTONE_BLOCK_ENTITY;
    public static final ScreenHandlerType<LodestoneScreenHandler> LODESTONE_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        TradefairMemoryModuleType.register();

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            RunUtil.runSafe(TradefairPointOfInterestTypes::register);
            RunUtil.runSafe(DecorTypes::registerAllPointsOfInterest);
        });
    }

    static {
        LODESTONE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier("lodestone"),
                FabricBlockEntityTypeBuilder.create(LodestoneBlockEntity::new, Blocks.LODESTONE).build(null));
        LODESTONE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier("lodestone"),
                new ScreenHandlerType<>(LodestoneScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    }
}