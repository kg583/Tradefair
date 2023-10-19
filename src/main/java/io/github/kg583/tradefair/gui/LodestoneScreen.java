package io.github.kg583.tradefair.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class LodestoneScreen extends HandledScreen<LodestoneScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/book.png");

    public final Text title;

    public final Map<VillagerProfession, List<Entity>> employment;
    public final double happiness;

    public LodestoneScreen(LodestoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.title = title;
        this.employment = getEmployment(handler).orElse(new HashMap<>());
        this.happiness = getHappiness(handler).orElse((double) 0);
    }

    private static Optional<Map<VillagerProfession, List<Entity>>> getEmployment(ScreenHandler handler) {
        if (handler instanceof LodestoneScreenHandler) {
            return Optional.of(((LodestoneScreenHandler) handler).employment);
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Double> getHappiness(ScreenHandler handler) {
        if (handler instanceof LodestoneScreenHandler) {
            return Optional.of(((LodestoneScreenHandler) handler).happiness);
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, (this.width - 192) / 2, 2, 0, 0, 192, 192);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = 28;
        int y = 0;

        context.drawText(this.textRenderer, this.title, x, y, 0, false);

        Map<String, Integer> employment =
                this.employment.entrySet().stream().collect(Collectors.toMap((entry) -> entry.getKey().id(),
                        (entry) -> entry.getValue().size()));

        int unemployed = 0;
        int baby = 0;
        for (Entity villager : this.employment.getOrDefault(VillagerProfession.NONE, new ArrayList<>())) {
            if (((VillagerEntity) villager).isBaby()) {
                baby += 1;
            } else {
                unemployed += 1;
            }
        }

        employment.put("baby", baby);
        employment.put("unemployed", unemployed);
        employment.remove("none");

        for (Map.Entry<String, Integer> entry : employment.entrySet()) {
            if (entry.getValue() > 0) {
                y += 16;

                context.drawText(this.textRenderer, Text.of(String.format("%dx %s", entry.getValue(),
                        StringUtils.capitalize(entry.getKey()))), x, y, 0, false);
            }
        }

        y += 32;
        context.drawText(this.textRenderer, Text.of(String.format("Happiness — %.2f", this.happiness)), x, y, 0,
                false);
    }
}
