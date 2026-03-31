package com.emanuelplays.notenoughobjectinfo.render;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

/** Shared rendering utilities for all NEOI HUD panels. */
public class RenderHelper {

    // Panel chrome
    public static final int LINE_H   = 10;
    public static final int PADDING  = 5;
    public static final int HDR_H    = 13;
    public static final int BAR_H    = 4;

    // ── Panel drawing ─────────────────────────────────────────────────────────

    /**
     * Draws a complete NEOI info panel and returns the Y coordinate
     * immediately below it (for stacking the next panel).
     */
    public static int drawPanel(GuiGraphics gui, Font font,
                                 int x, int y,
                                 List<String> lines,
                                 String header,
                                 int headerColour,
                                 int bgAlpha,
                                 boolean shadow) {
        if (lines.isEmpty()) return y;

        int maxW = font.width(stripCodes(header));
        for (String l : lines) maxW = Math.max(maxW, font.width(stripCodes(l)));

        int panelW = maxW + PADDING * 2 + 2;
        int panelH = HDR_H + lines.size() * LINE_H + PADDING * 2;

        if (bgAlpha > 0) {
            int bg = (bgAlpha << 24) | 0x070a10;
            gui.fill(x, y, x + panelW, y + panelH, bg);
            // Header bar
            int hdrBg = (Math.min(bgAlpha + 50, 255) << 24) | 0x0d1520;
            gui.fill(x, y, x + panelW, y + HDR_H, hdrBg);
            // Accent rule under header
            gui.fill(x, y + HDR_H, x + panelW, y + HDR_H + 1, headerColour | 0xFF000000);
            // Thin border
            gui.fill(x, y, x + 1, y + panelH, (bgAlpha << 24) | (headerColour & 0xFFFFFF));
        }

        // Header
        if (shadow) gui.drawString(font, header, x + PADDING, y + 2, headerColour | 0xFF000000, true);
        else        gui.drawString(font, header, x + PADDING, y + 2, headerColour | 0xFF000000, false);

        // Lines
        int lineY = y + HDR_H + PADDING;
        for (String line : lines) {
            if (shadow) gui.drawString(font, line, x + PADDING, lineY, 0xFFCDD9F5, true);
            else        gui.drawString(font, line, x + PADDING, lineY, 0xFFCDD9F5, false);
            lineY += LINE_H;
        }

        return y + panelH;
    }

    // ── Progress bars ─────────────────────────────────────────────────────────

    /**
     * Draws a horizontal progress bar, returns Y below it.
     */
    public static int drawBar(GuiGraphics gui, int x, int y, int width, float fraction,
                               int fillColour, int bgColour) {
        fraction = Math.max(0f, Math.min(1f, fraction));
        int filled = (int)(width * fraction);
        gui.fill(x, y, x + width, y + BAR_H, bgColour);
        if (filled > 0) gui.fill(x, y, x + filled, y + BAR_H, fillColour);
        return y + BAR_H + 2;
    }

    // ── Colour helpers ────────────────────────────────────────────────────────

    /** Green → Yellow → Red gradient based on fraction (0–1). */
    public static int fractionRgb(float fraction) {
        if (fraction > 0.6f) return 0xFF55FF55;
        if (fraction > 0.3f) return 0xFFFFAA00;
        return 0xFFFF5555;
    }

    /** §-code colour char for a fraction. */
    public static String fractionCode(float fraction) {
        if (fraction > 0.6f) return "§a";
        if (fraction > 0.3f) return "§e";
        return "§c";
    }

    /** §-code: §aYes / §cNo */
    public static String bool(boolean v) { return v ? "§aYes" : "§cNo"; }

    // ── Format helpers ────────────────────────────────────────────────────────

    public static String fmt(float v)  { return v == (int)v ? String.valueOf((int)v) : String.format("%.2f", v); }
    public static String fmt(double v) { return v == (long)v ? String.valueOf((long)v) : String.format("%.3f", v); }

    public static String pct(float v, float max) {
        if (max <= 0) return "0%";
        return String.format("%.0f%%", v / max * 100f);
    }

    public static String ticks(int ticks) {
        if (ticks <= 0) return "0s";
        int s = ticks / 20; int m = s / 60; s %= 60;
        return m > 0 ? m + "m " + s + "s" : s + "s";
    }

    public static String modName(String ns) {
        if ("minecraft".equals(ns)) return "Minecraft";
        if (ns == null || ns.isEmpty()) return "Unknown";
        return Character.toUpperCase(ns.charAt(0)) + ns.substring(1);
    }

    public static String trunc(String s, int max) {
        return s != null && s.length() > max ? s.substring(0, max) + "…" : s;
    }

    /** Strip Minecraft §-colour codes for width measurement. */
    public static String stripCodes(String s) {
        return s == null ? "" : s.replaceAll("§[0-9a-fk-or]", "");
    }
}
