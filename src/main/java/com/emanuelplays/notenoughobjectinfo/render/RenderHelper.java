package com.emanuelplays.notenoughobjectinfo.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {

    private static final int LINE_HEIGHT = 10;
    private static final int PADDING = 4;
    private static final int HEADER_HEIGHT = 12;

    /**
     * Draws the entire NEOI info panel.
     *
     * @param gui      GuiGraphics instance
     * @param font     Font renderer
     * @param x        Top-left X
     * @param y        Top-left Y
     * @param lines    Text lines to render (supports § colour codes)
     * @param header   Header text (section title)
     * @param bgAlpha  Background alpha 0-255
     * @param shadow   Whether to draw text shadow
     */
    public static int drawInfoPanel(
            GuiGraphics gui,
            Font font,
            int x, int y,
            List<String> lines,
            String header,
            int bgAlpha,
            boolean shadow
    ) {
        if (lines.isEmpty()) return y;

        // Calculate panel dimensions
        int maxWidth = font.width(header);
        for (String line : lines) {
            int w = font.width(line);
            if (w > maxWidth) maxWidth = w;
        }
        int panelWidth  = maxWidth + PADDING * 2;
        int panelHeight = HEADER_HEIGHT + lines.size() * LINE_HEIGHT + PADDING * 2;

        // Draw background
        if (bgAlpha > 0) {
            int bg = (bgAlpha << 24) | 0x101010;
            gui.fill(x - 1, y - 1, x + panelWidth + 1, y + panelHeight + 1, bg);
            // header bar
            int headerBg = (Math.min(bgAlpha + 60, 255) << 24) | 0x1A1A2E;
            gui.fill(x - 1, y - 1, x + panelWidth + 1, y + HEADER_HEIGHT + 1, headerBg);
            // accent line
            gui.fill(x - 1, y + HEADER_HEIGHT, x + panelWidth + 1, y + HEADER_HEIGHT + 1, 0xFFFFAA00);
        }

        // Draw header
        if (shadow) {
            gui.drawString(font, header, x + PADDING, y + 2, 0xFFFFAA00, true);
        } else {
            gui.drawString(font, header, x + PADDING, y + 2, 0xFFFFAA00, false);
        }

        // Draw lines
        int lineY = y + HEADER_HEIGHT + PADDING;
        for (String line : lines) {
            if (shadow) {
                gui.drawString(font, line, x + PADDING, lineY, 0xFFFFFFFF, true);
            } else {
                gui.drawString(font, line, x + PADDING, lineY, 0xFFFFFFFF, false);
            }
            lineY += LINE_HEIGHT;
        }

        return lineY + PADDING;
    }

    /**
     * Draws a coloured progress bar (used for health, durability, etc.).
     */
    public static void drawProgressBar(
            GuiGraphics gui,
            int x, int y,
            int width, int height,
            float fraction,
            int fullColour,
            int emptyColour,
            int bgColour
    ) {
        fraction = Math.max(0f, Math.min(1f, fraction));
        int filled = (int) (width * fraction);

        // Background
        gui.fill(x, y, x + width, y + height, bgColour);
        // Empty
        gui.fill(x, y, x + width, y + height, emptyColour);
        // Filled
        gui.fill(x, y, x + filled, y + height, fullColour);
        // Border
        gui.fill(x - 1, y - 1, x + width + 1, y,             0xFF444444);
        gui.fill(x - 1, y + height, x + width + 1, y + height + 1, 0xFF444444);
        gui.fill(x - 1, y - 1, x, y + height + 1,             0xFF444444);
        gui.fill(x + width, y - 1, x + width + 1, y + height + 1, 0xFF444444);
    }

    /**
     * Converts a health fraction to a colour: green → yellow → red.
     */
    public static int healthColour(float fraction) {
        if (fraction > 0.6f) return 0xFF55FF55;
        if (fraction > 0.3f) return 0xFFFFFF55;
        return 0xFFFF5555;
    }

    /**
     * Converts a durability fraction to a colour: green → yellow → red.
     */
    public static int durabilityColour(float fraction) {
        if (fraction > 0.6f) return 0xFF55FF55;
        if (fraction > 0.3f) return 0xFFFFAA00;
        return 0xFFFF5555;
    }

    /**
     * Pretty-formats a float, removing trailing zeros.
     */
    public static String fmt(float v) {
        if (v == (int) v) return String.valueOf((int) v);
        return String.format("%.2f", v);
    }

    public static String fmt(double v) {
        if (v == (long) v) return String.valueOf((long) v);
        return String.format("%.3f", v);
    }

    /**
     * Returns a colour code char based on a value vs maximum.
     * > 60% → §a (green), > 30% → §e (yellow), else → §c (red)
     */
    public static String fractionColour(float value, float max) {
        float fraction = max > 0 ? value / max : 0;
        if (fraction > 0.6f) return "§a";
        if (fraction > 0.3f) return "§e";
        return "§c";
    }
}
