package io.github.kg583.tradefair.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public class LodestoneScreen extends Screen {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/book.png");

    public final MutableText name;

    public LodestoneScreen(MutableText name) {
        super(NarratorManager.EMPTY);

        this.name = name;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int k = this.textRenderer.getWidth(this.name);
        context.drawText(this.textRenderer, this.name, (this.width - 192) / 2 + 192 - k - 44, 18, 0, false);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(TEXTURE, (this.width - 192) / 2, 2, 0, 0, 192, 192);
    }
}
