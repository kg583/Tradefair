package io.github.kg583.tradefair;

import io.github.kg583.tradefair.gui.LodestoneScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

import static io.github.kg583.tradefair.Main.LODESTONE_SCREEN_HANDLER;

public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(LODESTONE_SCREEN_HANDLER, LodestoneScreen::new);
    }
}
