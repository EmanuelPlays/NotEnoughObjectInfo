package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BlockInfoProvider {

    public record BlockData(
            String displayName,
            String registryId,
            String modId,
            float hardness,
            float blastResistance,
            String harvestTool,
            int blockLight,
            int skyLight,
            int redstonePower,
            boolean isFlammable,
            int flammability,
            int fireSpreadSpeed,
            boolean isReplaceable,
            boolean isSolid,
            float friction,
            float speedFactor,
            float jumpFactor,
            List<String> blockStateProperties,
            List<String> tags,
            String mapColor,
            boolean hasTileEntity,
            String tileEntityType,
            String compactNbt
    ) {}

    public static BlockData fromState(Level level, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        ResourceLocation rl = ForgeRegistries.BLOCKS.getKey(block);

        String registryId = rl != null ? rl.toString() : "unknown:unknown";
        String modId      = rl != null ? rl.getNamespace() : "unknown";
        String displayName = block.getName().getString();

        // Hardness & resistance
        float hardness  = state.getDestroySpeed(level, pos);
        float resistance = block.getExplosionResistance();

        // Harvest tool
        String harvestTool = getHarvestTool(state);

        // Light
        int blockLight = level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK, pos);
        int skyLight   = level.getBrightness(net.minecraft.world.level.LightLayer.SKY, pos);

        // Redstone
        int redstonePower = level.getBestNeighborSignal(pos);

        // Flammability
        boolean isFlammable    = block.isFlammable(state, level, pos, net.minecraft.core.Direction.UP);
        int flammability       = block.getFlammability(state, level, pos, net.minecraft.core.Direction.UP);
        int fireSpreadSpeed    = block.getFireSpreadSpeed(state, level, pos, net.minecraft.core.Direction.UP);

        // Physical properties
        boolean isReplaceable  = state.canBeReplaced();
        boolean isSolid        = state.isSolidRender(level, pos);
        float friction         = block.getFriction(state, level, pos, null);
        float speedFactor      = block.getSpeedFactor();
        float jumpFactor       = block.getJumpFactor();

        // Block state properties
        List<String> stateProps = new ArrayList<>();
        for (Map.Entry<Property<?>, Comparable<?>> entry : state.getValues().entrySet()) {
            stateProps.add(entry.getKey().getName() + "=" + entry.getValue());
        }

        // Tags
        List<String> tags = new ArrayList<>();
        for (TagKey<Block> tag : state.getTags().toList()) {
            tags.add("#" + tag.location());
        }

        // Map color
        String mapColor = state.getMapColor(level, pos).toString();

        // Tile entity
        boolean hasTileEntity  = state.hasBlockEntity();
        String tileEntityType  = "";
        String compactNbt      = "";
        if (hasTileEntity) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                ResourceLocation teRl = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(be.getType());
                tileEntityType = teRl != null ? teRl.toString() : "unknown";
                CompoundTag tag = be.saveWithoutMetadata();
                compactNbt = compactNbt(tag, 256);
            }
        }

        return new BlockData(
                displayName, registryId, modId, hardness, resistance,
                harvestTool, blockLight, skyLight, redstonePower,
                isFlammable, flammability, fireSpreadSpeed,
                isReplaceable, isSolid, friction, speedFactor, jumpFactor,
                stateProps, tags, mapColor,
                hasTileEntity, tileEntityType, compactNbt
        );
    }

    private static String getHarvestTool(BlockState state) {
        if (state.requiresCorrectToolForDrops()) {
            // Try each common tool tag
            if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE))   return "Pickaxe";
            if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE))       return "Axe";
            if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL))    return "Shovel";
            if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE))       return "Hoe";
            if (state.is(net.minecraft.tags.BlockTags.SWORD_EFFICIENT))         return "Sword";
            return "Requires Tool";
        }
        // Check anyway for display
        if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE))       return "Pickaxe";
        if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE))           return "Axe";
        if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL))        return "Shovel";
        if (state.is(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE))           return "Hoe";
        return "Any";
    }

    /** Serialise NBT tag to a human-readable string capped at maxChars. */
    public static String compactNbt(CompoundTag tag, int maxChars) {
        StringBuilder sb = new StringBuilder("{");
        List<String> keys = new ArrayList<>(tag.getAllKeys());
        Collections.sort(keys);
        for (String key : keys) {
            if (sb.length() > maxChars) { sb.append("..."); break; }
            sb.append(key).append(":").append(tag.get(key)).append(", ");
        }
        if (sb.length() > 1 && sb.charAt(sb.length() - 2) == ',') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }
}
