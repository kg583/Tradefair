package io.github.kg583.tradefair.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LodestoneScreen extends HandledScreen<LodestoneScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/book.png");

    public final Text title;

    public LodestoneScreen(LodestoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.title = title;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, (this.width - 192) / 2, 2, 0, 0, 192, 192);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int k = this.textRenderer.getWidth(this.title);
        context.drawText(this.textRenderer, this.title, (this.width - 192) / 2 - k - 44, 18, 0, false);
    }
}
