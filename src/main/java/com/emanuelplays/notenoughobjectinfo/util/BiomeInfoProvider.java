package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class BiomeInfoProvider {

    public record BiomeData(
            String displayName,
            String registryId,
            String modId,
            float temperature,
            String precipitation,
            float downfall,
            boolean isHumid,
            String temperatureCategory,
            List<String> tags
    ) {}

    public static BiomeData fromPos(Level level, BlockPos pos) {
        Holder<Biome> holder = level.getBiome(pos);
        Biome biome = holder.value();

        // Get registry key
        ResourceLocation rl = holder.unwrapKey()
                .map(k -> k.location())
                .orElse(null);

        String registryId = rl != null ? rl.toString() : "unknown:unknown";
        String modId      = rl != null ? rl.getNamespace() : "unknown";

        // Friendly display name: capitalise path with spaces
        String displayName = rl != null
                ? toDisplayName(rl.getPath())
                : "Unknown Biome";

        float temperature  = biome.getBaseTemperature();
        float downfall     = biome.getModifiedClimateSettings().downfall();
        boolean isHumid    = downfall > 0.85f;

        // Precipitation type
        String precipitation = switch (biome.getPrecipitationAt(pos)) {
            case RAIN  -> "Rain";
            case SNOW  -> "Snow";
            default    -> "None";
        };

        // Temperature category label
        String tempCategory;
        if (temperature < 0.0f)      tempCategory = "§9Frozen";
        else if (temperature < 0.15f) tempCategory = "§bCold";
        else if (temperature < 0.95f) tempCategory = "§aTemperate";
        else                          tempCategory = "§6Warm";

        // Tags from holder
        List<String> tags = new ArrayList<>();
        holder.tags().map(t -> "#" + t.location()).forEach(tags::add);

        return new BiomeData(
                displayName, registryId, modId,
                temperature, precipitation, downfall,
                isHumid, tempCategory, tags
        );
    }

    private static String toDisplayName(String path) {
        String[] parts = path.replace('_', ' ').split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty())
                sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)).append(' ');
        }
        return sb.toString().trim();
    }
}
