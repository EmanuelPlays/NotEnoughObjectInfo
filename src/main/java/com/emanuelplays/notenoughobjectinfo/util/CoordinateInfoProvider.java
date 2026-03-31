package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class CoordinateInfoProvider {

    public record CoordData(
            int x, int y, int z,
            double exactX, double exactY, double exactZ,
            int chunkX, int chunkZ,
            int chunkLocalX, int chunkLocalZ,
            int regionX, int regionZ,
            String facing, String facingExact,
            double yaw, double pitch,
            String dimensionId,
            int netherX, int netherZ,
            int overworldX, int overworldZ,
            boolean isInNether
    ) {}

    public static CoordData from(LocalPlayer player) {
        Vec3 pos = player.position();
        int x = (int) Math.floor(pos.x);
        int y = (int) Math.floor(pos.y);
        int z = (int) Math.floor(pos.z);

        int chunkX      = x >> 4;
        int chunkZ      = z >> 4;
        int chunkLocalX = x & 15;
        int chunkLocalZ = z & 15;
        int regionX     = chunkX >> 5;
        int regionZ     = chunkZ >> 5;

        // Cardinal facing from yaw
        float yaw = player.getYRot() % 360;
        if (yaw < 0) yaw += 360;
        String facing;
        if      (yaw < 22.5f  || yaw >= 337.5f) facing = "South (+Z)";
        else if (yaw < 67.5f)                   facing = "South-West";
        else if (yaw < 112.5f)                  facing = "West (-X)";
        else if (yaw < 157.5f)                  facing = "North-West";
        else if (yaw < 202.5f)                  facing = "North (-Z)";
        else if (yaw < 247.5f)                  facing = "North-East";
        else if (yaw < 292.5f)                  facing = "East (+X)";
        else                                     facing = "South-East";

        String facingExact = String.format("%.1f° / %.1f°", yaw, player.getXRot());

        String dimensionId = player.level().dimension().location().toString();
        boolean isInNether = "minecraft:the_nether".equals(dimensionId);

        // Portal coordinates (Nether ↔ Overworld are 1:8)
        int netherX, netherZ, overworldX, overworldZ;
        if (isInNether) {
            netherX    = x;      netherZ    = z;
            overworldX = x * 8; overworldZ = z * 8;
        } else {
            overworldX = x;     overworldZ = z;
            netherX    = x / 8; netherZ    = z / 8;
        }

        return new CoordData(
                x, y, z,
                pos.x, pos.y, pos.z,
                chunkX, chunkZ,
                chunkLocalX, chunkLocalZ,
                regionX, regionZ,
                facing, facingExact,
                yaw, player.getXRot(),
                dimensionId,
                netherX, netherZ,
                overworldX, overworldZ,
                isInNether
        );
    }
}
