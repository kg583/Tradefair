package io.github.kg583.tradefair.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LodestoneScreen extends HandledScreen<LodestoneScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/book.png");

    public final Text title;
    public final Map<VillagerProfession, List<Entity>> employment;

    public LodestoneScreen(LodestoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.title = title;
        this.employment = getEmployment(handler).orElse(new HashMap<>());
    }

    private static Optional<Map<VillagerProfession, List<Entity>>> getEmployment(ScreenHandler handler) {
        if (handler instanceof LodestoneScreenHandler) {
            return Optional.of(((LodestoneScreenHandler) handler).employment);
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
        context.drawText(this.textRenderer, this.title, 28, 0, 0, false);

        int y = 0;
        for (Map.Entry<VillagerProfession, List<Entity>> entry : employment.entrySet()) {
            y += 16;
            context.drawText(this.textRenderer, Text.of(String.format("%dx %s", entry.getValue().size(),
                    StringUtils.capitalize(entry.getKey().id()))), 28, y, 0, false);
        }
    }
}
