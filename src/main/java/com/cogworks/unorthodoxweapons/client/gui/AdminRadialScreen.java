package com.cogworks.unorthodoxweapons.client.gui;

import com.cogworks.unorthodoxweapons.items.admin.AdminGunMode;
import com.cogworks.unorthodoxweapons.network.AdminModePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class AdminRadialScreen extends Screen {

    private static final AdminGunMode[] MODES = AdminGunMode.values();
    private static final int RADIUS = 60;

    public AdminRadialScreen() {
        super(Component.literal("Admin Gun Mode"));
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        int cx = width / 2;
        int cy = height / 2;

        for (int i = 0; i < MODES.length; i++) {
            double angle = (2 * Math.PI / MODES.length) * i - Math.PI / 2;
            int x = cx + (int) (Math.cos(angle) * RADIUS);
            int y = cy + (int) (Math.sin(angle) * RADIUS);
            boolean hovered = isHovered(mouseX, mouseY, x, y);
            graphics.fill(x - 20, y - 10, x + 20, y + 10, hovered ? 0xAA00FF00 : 0xAA333333);
            graphics.drawCenteredString(font, MODES[i].name(), x, y - 4, 0xFFFFFF);
        }
    }

    private boolean isHovered(int mx, int my, int x, int y) {
        return mx >= x - 20 && mx <= x + 20 && my >= y - 10 && my <= y + 10;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cx = width / 2;
        int cy = height / 2;

        for (int i = 0; i < MODES.length; i++) {
            double angle = (2 * Math.PI / MODES.length) * i - Math.PI / 2;
            int x = cx + (int) (Math.cos(angle) * RADIUS);
            int y = cy + (int) (Math.sin(angle) * RADIUS);
            if (isHovered((int) mouseX, (int) mouseY, x, y)) {
                PacketDistributor.sendToServer(new AdminModePayload(MODES[i]));
                onClose();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}